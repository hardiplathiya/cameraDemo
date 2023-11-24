package com.iphonecamera.allinone.cameraediting.util;


public class FakeThreadUtils {

    public static void postTask(Runnable runnable) {
        new Thread(runnable).start();
    }
}
