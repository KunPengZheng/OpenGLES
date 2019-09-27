/*
 *
 * CameraView.java
 *
 * Created by Wuwang on 2016/11/14
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.example.opengles.camera;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import com.example.opengles.renderer.CameraRenderer;

/**
 * Description:
 */
public class CameraView extends GLSurfaceView {

    private CameraRenderer cameraRenderer;
    private KitkatCamera mCamera2;
    private Context context;

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private void init() {
        cameraRenderer = new CameraRenderer(context);

        setEGLContextClientVersion(2);
        setRenderer(cameraRenderer);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        mCamera2 = new KitkatCamera();
    }

    public void switchCamera() {

    }
}
