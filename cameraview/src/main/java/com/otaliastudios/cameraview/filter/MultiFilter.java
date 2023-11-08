package com.otaliastudios.cameraview.filter;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.otaliastudios.cameraview.size.Size;
import com.otaliastudios.opengl.core.Egloo;
import com.otaliastudios.opengl.program.GlProgram;
import com.otaliastudios.opengl.program.GlTextureProgram;
import com.otaliastudios.opengl.texture.GlFramebuffer;
import com.otaliastudios.opengl.texture.GlTexture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class MultiFilter implements Filter, OneParameterFilter, TwoParameterFilter {

    @VisibleForTesting
    static class State {
        @VisibleForTesting boolean isProgramCreated = false;
        @VisibleForTesting boolean isFramebufferCreated = false;
        private boolean sizeChanged = false;
        @VisibleForTesting Size size = null;
        private int programHandle = -1;
        private GlFramebuffer outputFramebuffer = null;
        private GlTexture outputTexture = null;
    }

    @VisibleForTesting final List<Filter> filters = new ArrayList<>();
    @VisibleForTesting final Map<Filter, State> states = new HashMap<>();
    private final Object lock = new Object();
    private Size size = null;
    private float parameter1 = 0F;
    private float parameter2 = 0F;

    public MultiFilter(@NonNull Filter... filters) {
        this(Arrays.asList(filters));
    }

    @SuppressWarnings("WeakerAccess")
    public MultiFilter(@NonNull Collection<Filter> filters) {
        for (Filter filter : filters) {
            addFilter(filter);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void addFilter(@NonNull Filter filter) {
        if (filter instanceof MultiFilter) {
            MultiFilter multiFilter = (MultiFilter) filter;
            for (Filter multiChild : multiFilter.filters) {
                addFilter(multiChild);
            }
            return;
        }
        synchronized (lock) {
            if (!filters.contains(filter)) {
                filters.add(filter);
                states.put(filter, new State());
            }
        }
    }

    private void maybeCreateProgram(@NonNull Filter filter, boolean isFirst, boolean isLast) {
        State state = states.get(filter);
        if (state.isProgramCreated) return;
        state.isProgramCreated = true;

        String fragmentShader = isFirst
                ? filter.getFragmentShader()
                : filter.getFragmentShader().replace("samplerExternalOES ", "sampler2D ");
        String vertexShader = filter.getVertexShader();
        state.programHandle = GlProgram.create(vertexShader, fragmentShader);
        filter.onCreate(state.programHandle);
    }

    private void maybeDestroyProgram(@NonNull Filter filter) {
        State state = states.get(filter);
        if (!state.isProgramCreated) return;
        state.isProgramCreated = false;
        filter.onDestroy();
        GLES20.glDeleteProgram(state.programHandle);
        state.programHandle = -1;
    }

    private void maybeCreateFramebuffer(@NonNull Filter filter, boolean isFirst, boolean isLast) {
        State state = states.get(filter);
        if (isLast) {
            state.sizeChanged = false;
            return;
        }
        if (state.sizeChanged) {
            maybeDestroyFramebuffer(filter);
            state.sizeChanged = false;
        }
        if (!state.isFramebufferCreated) {
            state.isFramebufferCreated = true;
            state.outputTexture = new GlTexture(GLES20.GL_TEXTURE0,
                    GLES20.GL_TEXTURE_2D,
                    state.size.getWidth(),
                    state.size.getHeight());
            state.outputFramebuffer = new GlFramebuffer();
            state.outputFramebuffer.attach(state.outputTexture);
        }
    }

    private void maybeDestroyFramebuffer(@NonNull Filter filter) {
        State state = states.get(filter);
        if (!state.isFramebufferCreated) return;
        state.isFramebufferCreated = false;
        state.outputFramebuffer.release();
        state.outputFramebuffer = null;
        state.outputTexture.release();
        state.outputTexture = null;
    }

    private void maybeSetSize(@NonNull Filter filter) {
        State state = states.get(filter);
        if (size != null && !size.equals(state.size)) {
            state.size = size;
            state.sizeChanged = true;
            filter.setSize(size.getWidth(), size.getHeight());
        }
    }

    @Override
    public void onCreate(int programHandle) {
    }

    @NonNull
    @Override
    public String getVertexShader() {
        return GlTextureProgram.SIMPLE_VERTEX_SHADER;
    }

    @NonNull
    @Override
    public String getFragmentShader() {
        return GlTextureProgram.SIMPLE_FRAGMENT_SHADER;
    }

    @Override
    public void onDestroy() {
        synchronized (lock) {
            for (Filter filter : filters) {
                maybeDestroyFramebuffer(filter);
                maybeDestroyProgram(filter);
            }
        }
    }

    @Override
    public void setSize(int width, int height) {
        size = new Size(width, height);
        synchronized (lock) {
            for (Filter filter : filters) {
                maybeSetSize(filter);
            }
        }
    }

    @Override
    public void draw(long timestampUs, @NonNull float[] transformMatrix) {
        synchronized (lock) {
            for (int i = 0; i < filters.size(); i++) {
                boolean isFirst = i == 0;
                boolean isLast = i == filters.size() - 1;
                Filter filter = filters.get(i);
                State state = states.get(filter);

                maybeSetSize(filter);
                maybeCreateProgram(filter, isFirst, isLast);
                maybeCreateFramebuffer(filter, isFirst, isLast);

                GLES20.glUseProgram(state.programHandle);

                if (!isLast) {
                    state.outputFramebuffer.bind();
                    GLES20.glClearColor(0, 0, 0, 0);
                } else {
                    GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
                }

                if (isFirst) {
                    filter.draw(timestampUs, transformMatrix);
                } else {
                    filter.draw(timestampUs, Egloo.IDENTITY_MATRIX);
                }

                if (!isLast) {
                    state.outputTexture.bind();
                } else {
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
                    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                }

                GLES20.glUseProgram(0);
            }
        }
    }

    @NonNull
    @Override
    public Filter copy() {
        synchronized (lock) {
            MultiFilter copy = new MultiFilter();
            if (size != null) {
                copy.setSize(size.getWidth(), size.getHeight());
            }
            for (Filter filter : filters) {
                copy.addFilter(filter.copy());
            }
            return copy;
        }
    }

    @Override
    public void setParameter1(float parameter1) {
        this.parameter1 = parameter1;
        synchronized (lock) {
            for (Filter filter : filters) {
                if (filter instanceof OneParameterFilter) {
                    ((OneParameterFilter) filter).setParameter1(parameter1);
                }
            }
        }
    }

    @Override
    public void setParameter2(float parameter2) {
        this.parameter2 = parameter2;
        synchronized (lock) {
            for (Filter filter : filters) {
                if (filter instanceof TwoParameterFilter) {
                    ((TwoParameterFilter) filter).setParameter2(parameter2);
                }
            }
        }
    }

    @Override
    public float getParameter1() {
        return parameter1;
    }

    @Override
    public float getParameter2() {
        return parameter2;
    }
}
