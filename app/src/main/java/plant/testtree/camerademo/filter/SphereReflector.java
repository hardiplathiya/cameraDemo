package plant.testtree.camerademo.filter;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import plant.testtree.camerademo.util.TextureUtils;


public class SphereReflector extends PassThroughFilter {
    private GLPassThroughProgram glSphereProgram;
    private float[] mMVPMatrix;
    private float[] modelMatrix;
    private float[] modelViewMatrix;
    private float ratio;
    private Sphere sphere;
    private float[] viewMatrix;

    public SphereReflector(Context context) {
        super(context);
        this.mMVPMatrix = new float[16];
        this.modelMatrix = new float[16];
        this.modelViewMatrix = new float[16];
        this.sphere = new Sphere(8.0f, 75, 150);
        this.viewMatrix = new float[16];
        this.glSphereProgram = new GLPassThroughProgram(context);
        initMatrix();
    }

    @Override 
    public void init() {
        super.init();
        this.glSphereProgram.create();
    }

    @Override 
    public void destroy() {
        super.destroy();
        this.glSphereProgram.onDestroy();
    }

    @Override 
    public void onDrawFrame(int i) {
        super.onDrawFrame(i);
        this.glSphereProgram.use();
        this.sphere.uploadTexCoordinateBuffer(this.glSphereProgram.getTextureCoordinateHandle());
        this.sphere.uploadVerticesBuffer(this.glSphereProgram.getPositionHandle());
        Matrix.perspectiveM(this.projectionMatrix, 0, 90.0f, this.ratio, 1.0f, 500.0f);
        Matrix.multiplyMM(this.modelViewMatrix, 0, this.viewMatrix, 0, this.modelMatrix, 0);
        Matrix.multiplyMM(this.mMVPMatrix, 0, this.projectionMatrix, 0, this.modelViewMatrix, 0);
        GLES20.glUniformMatrix4fv(this.glSphereProgram.getMVPMatrixHandle(), 1, false, this.mMVPMatrix, 0);
        TextureUtils.bindTexture2D(i, 33984, this.glSphereProgram.getTextureSamplerHandle(), 0);
        this.sphere.draw();
    }

    @Override 
    public void onFilterChanged(int i, int i2) {
        super.onFilterChanged(i, i2);
        this.ratio = i / i2;
    }

    private void initMatrix() {
        Matrix.setIdentityM(this.modelMatrix, 0);
        Matrix.rotateM(this.modelMatrix, 0, 90.0f, 0.0f, 1.0f, 0.0f);
        Matrix.setIdentityM(this.projectionMatrix, 0);
        Matrix.setIdentityM(this.viewMatrix, 0);
        Matrix.setLookAtM(this.viewMatrix, 0, 0.0f, 10.0f, 10.0f, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);
    }
}
