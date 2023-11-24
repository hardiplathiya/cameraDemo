package com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu;

import android.content.Context;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

import com.iphonecamera.allinone.cameraediting.filter.MultipleTextureFilter;
import com.iphonecamera.allinone.cameraediting.util.FileUtils;
import com.iphonecamera.allinone.cameraediting.util.ShaderUtils;


public class XiuXiuXiuAbsFilter extends MultipleTextureFilter {
    public static final boolean DUMP_DATA = false;
    private Vector<BitmapFileDescription> bitmapFileDescriptions;
    private ByteBuffer mDataBuffer;

   
    public class BitmapFileDescription {
        int endPos;
        String name;
        int startPos;

        public BitmapFileDescription(String str, int i, int i2) {
           // XiuXiuXiuAbsFilter.this = r1;
            this.name = str;
            this.startPos = i;
            this.endPos = i2;
        }

        public String toString() {
            return "name: " + this.name + " startPos: " + this.startPos + " endPos: " + this.endPos;
        }
    }

    public XiuXiuXiuAbsFilter(Context context, String str, String str2, String str3) {
        super(context, str);
        readData(str2, str3);
    }

    @Override 
    public void init() {
        super.init();
        for (int i = 0; i < this.textureSize; i++) {
            BitmapFileDescription bitmapFileDescription = this.bitmapFileDescriptions.get(i);
            this.externalBitmapTextures[i].loadBitmap(BitmapFactory.decodeByteArray(this.mDataBuffer.array(), this.mDataBuffer.arrayOffset() + bitmapFileDescription.startPos, bitmapFileDescription.endPos));
        }
    }

    private void readData(String str, String str2) {
        this.bitmapFileDescriptions = new Vector<>();
        String str3 = "tempFile." + System.currentTimeMillis();

        FileUtils.copyFileFromAssets(this.context, this.context.getCacheDir().getAbsolutePath(), str3, str2);
        File file = new File(this.context.getCacheDir().getAbsolutePath(), str3);
        if (file.exists()) {
            for (String str4 : ShaderUtils.readAssetsTextFile(this.context, str).split(";")) {
                String[] split = str4.split(":");
                if (split.length == 3) {
                    this.bitmapFileDescriptions.add(new BitmapFileDescription(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2])));
                }
            }
            this.mDataBuffer = ByteBuffer.allocateDirect((int) file.length());
            byte[] bArr = new byte[512];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    this.mDataBuffer.put(bArr, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.textureSize = this.bitmapFileDescriptions.size();
            file.delete();
        }
    }
}
