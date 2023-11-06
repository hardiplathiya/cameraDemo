package com.otaliastudios.cameraview;

import android.os.Build;
import com.otaliastudios.cameraview.controls.Control;
import com.otaliastudios.cameraview.controls.Engine;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Flash;
import com.otaliastudios.cameraview.controls.Hdr;
import com.otaliastudios.cameraview.controls.WhiteBalance;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class Mapper {
    private static Mapper CAMERA1;
    private static Mapper CAMERA2;

    public abstract <T> T map(Facing facing);

    public abstract <T> T map(Flash flash);

    public abstract <T> T map(Hdr hdr);

    public abstract <T> T map(WhiteBalance whiteBalance);

    public abstract <T> Facing unmapFacing(T t);

    public abstract <T> Flash unmapFlash(T t);

    public abstract <T> Hdr unmapHdr(T t);

    public abstract <T> WhiteBalance unmapWhiteBalance(T t);

    public static Mapper get(Engine engine) {
        if (engine == Engine.CAMERA1) {
            if (CAMERA1 == null) {
                CAMERA1 = new Camera1Mapper();
            }
            return CAMERA1;
        } else if (engine == Engine.CAMERA2 && Build.VERSION.SDK_INT >= 21) {
            if (CAMERA2 == null) {
                CAMERA2 = new Camera2Mapper();
            }
            return CAMERA2;
        } else {
            throw new IllegalArgumentException("Unknown engine or unsupported API level.");
        }
    }

    private Mapper() {
    }

    protected <C extends Control, T> C reverseLookup(HashMap<C, T> hashMap, T t) {
        for (C c : hashMap.keySet()) {
            if (t.equals(hashMap.get(c))) {
                return c;
            }
        }
        return null;
    }

    protected <C extends Control, T> C reverseListLookup(HashMap<C, List<T>> hashMap, T t) {
        for (C c : hashMap.keySet()) {
            List<T> list = hashMap.get(c);
            if (list != null) {
                for (T t2 : list) {
                    if (t.equals(t2)) {
                        return c;
                    }
                }
                continue;
            }
        }
        return null;
    }

    /* loaded from: classes2.dex */
    public static class Camera1Mapper extends Mapper {
        private static final HashMap<Flash, String> FLASH = new HashMap<>();
        private static final HashMap<WhiteBalance, String> WB = new HashMap<>();
        private static final HashMap<Facing, Integer> FACING = new HashMap<>();
        private static final HashMap<Hdr, String> HDR = new HashMap<>();

        private Camera1Mapper() {
            super();
        }

        static {
            FLASH.put(Flash.OFF, "off");
            FLASH.put(Flash.ON, "on");
            FLASH.put(Flash.AUTO, "auto");
            FLASH.put(Flash.TORCH, "torch");
            FACING.put(Facing.BACK, 0);
            FACING.put(Facing.FRONT, 1);
            WB.put(WhiteBalance.AUTO, "auto");
            WB.put(WhiteBalance.INCANDESCENT, "incandescent");
            WB.put(WhiteBalance.FLUORESCENT, "fluorescent");
            WB.put(WhiteBalance.DAYLIGHT, "daylight");
            WB.put(WhiteBalance.CLOUDY, "cloudy-daylight");
            HDR.put(Hdr.OFF, "auto");
            if (Build.VERSION.SDK_INT >= 17) {
                HDR.put(Hdr.ON, "hdr");
            } else {
                HDR.put(Hdr.ON, "hdr");
            }
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> T map(Flash flash) {
            return (T) FLASH.get(flash);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> T map(Facing facing) {
            return (T) FACING.get(facing);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> T map(WhiteBalance whiteBalance) {
            return (T) WB.get(whiteBalance);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> T map(Hdr hdr) {
            return (T) HDR.get(hdr);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> Flash unmapFlash(T t) {
            return (Flash) reverseLookup(FLASH, (String) t);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> Facing unmapFacing(T t) {
            return (Facing) reverseLookup(FACING, (Integer) t);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> WhiteBalance unmapWhiteBalance(T t) {
            return (WhiteBalance) reverseLookup(WB, (String) t);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> Hdr unmapHdr(T t) {
            return (Hdr) reverseLookup(HDR, (String) t);
        }
    }

    /* loaded from: classes2.dex */
    public static class Camera2Mapper extends Mapper {
        private static final HashMap<Flash, List<Integer>> FLASH = new HashMap<>();
        private static final HashMap<Facing, Integer> FACING = new HashMap<>();
        private static final HashMap<WhiteBalance, Integer> WB = new HashMap<>();
        private static final HashMap<Hdr, Integer> HDR = new HashMap<>();

        private Camera2Mapper() {
            super();
        }

        static {
            FLASH.put(Flash.OFF, Arrays.asList(1, 0));
            FLASH.put(Flash.TORCH, Arrays.asList(1, 0));
            FLASH.put(Flash.AUTO, Arrays.asList(2, 4));
            FLASH.put(Flash.ON, Collections.singletonList(3));
            FACING.put(Facing.BACK, 1);
            FACING.put(Facing.FRONT, 0);
            WB.put(WhiteBalance.AUTO, 1);
            WB.put(WhiteBalance.CLOUDY, 6);
            WB.put(WhiteBalance.DAYLIGHT, 5);
            WB.put(WhiteBalance.FLUORESCENT, 3);
            WB.put(WhiteBalance.INCANDESCENT, 2);
            HDR.put(Hdr.OFF, 0);
            HDR.put(Hdr.ON, 18);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> T map(Flash flash) {
            return (T) FLASH.get(flash);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> T map(Facing facing) {
            return (T) FACING.get(facing);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> T map(WhiteBalance whiteBalance) {
            return (T) WB.get(whiteBalance);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> T map(Hdr hdr) {
            return (T) HDR.get(hdr);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> Flash unmapFlash(T t) {
            return (Flash) reverseListLookup(FLASH, (Integer) t);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> Facing unmapFacing(T t) {
            return (Facing) reverseLookup(FACING, (Integer) t);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> WhiteBalance unmapWhiteBalance(T t) {
            return (WhiteBalance) reverseLookup(WB, (Integer) t);
        }

        @Override // com.otaliastudios.cameraview.engine.Mapper
        public <T> Hdr unmapHdr(T t) {
            return (Hdr) reverseLookup(HDR, (Integer) t);
        }
    }
}
