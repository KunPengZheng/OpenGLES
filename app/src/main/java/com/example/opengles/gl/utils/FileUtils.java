package com.example.opengles.gl.utils;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.RawRes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created b Zwp on 2019/6/13.
 */
public class FileUtils {
    /**
     * assets 文件转为字符串
     *
     * @param context
     * @param file    文件路径
     * @return
     */
    public static String assets2String(Context context, String file) {
        if (context == null || TextUtils.isEmpty(file)) {
            return null;
        }

        String result = null;
        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
        byte[] buff = new byte[1024];
        try {
            is = context.getAssets().open(file);
            int readSize;
            while ((readSize = is.read(buff)) > -1) {
                baos.write(buff, 0, readSize);
            }
            result = new String(baos.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * raw 资源转为字符串
     *
     * @param context
     * @param rawId   资源id
     * @return
     */
    public static String raw2String(Context context, @RawRes int rawId) {
        if (context == null || rawId == -1) {
            return null;
        }

        String result = null;
        InputStream is = context.getResources().openRawResource(rawId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
        byte[] buff = new byte[1024];
        int readSize;

        try {
            while ((readSize = is.read(buff)) > -1) {
                baos.write(buff, 0, readSize);
            }

            result = new String(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
