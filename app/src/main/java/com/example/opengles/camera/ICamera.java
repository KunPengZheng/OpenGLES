/*
 *
 * ICamera.java
 *
 * Created by Wuwang on 2016/11/10
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.example.opengles.camera;

import android.graphics.Point;
import android.graphics.SurfaceTexture;

/**
 * Description:
 */
public interface ICamera {

    /**
     * 打开指定镜头
     *
     * @param cameraId 镜头id
     * @return boolean
     */
    boolean open(int cameraId);

    /**
     * 设置配置
     *
     * @param config
     */
    void setConfig(Config config);

    /**
     * 预览
     *
     * @return
     */
    boolean preview();

    /**
     * 切换前后置
     *
     * @param cameraId
     * @return
     */
    boolean switchTo(int cameraId);

    /**
     * 拍照
     *
     * @param callback
     */
    void takePhoto(TakePhotoCallback callback);

    /**
     * 关闭
     *
     * @return
     */
    boolean close();

    /**
     * 设置预览纹理
     *
     * @param texture
     */
    void setPreviewTexture(SurfaceTexture texture);

    /**
     * 设置预览大小
     *
     * @return
     */
    Point getPreviewSize();


    /**
     * 设置生成图片的大小
     *
     * @return
     */
    Point getPictureSize();


    void setOnPreviewFrameCallback(PreviewFrameCallback callback);

    class Config {
        float rate; //宽高比
        int minPreviewWidth;
        int minPictureWidth;
    }

    interface TakePhotoCallback {
        void onTakePhoto(byte[] bytes, int width, int height);
    }

    interface PreviewFrameCallback {
        void onPreviewFrame(byte[] bytes, int width, int height);
    }

}
