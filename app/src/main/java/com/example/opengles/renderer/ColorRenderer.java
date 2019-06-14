package com.example.opengles.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import com.example.opengles.gl.GLColorLayer;
import com.example.opengles.gl.utils.GLMatrixUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created b Zwp on 2019/6/13.
 */
public class ColorRenderer implements GLSurfaceView.Renderer {

    private final Context context;
    private GLColorLayer glColorLayer;
    private int width;
    private int height;

    public ColorRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 成员变量和构造方法的创建方式都还不是gl环境
        // 这些回调方法才是gl环境
        // 一定要放在gl环境初始化，否则创建不成功 ！！！ 提示着色器创建失败
        glColorLayer = new GLColorLayer(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glViewport(0, 0, width, height);
        glColorLayer.setProjectOrtho(width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        float[] identityMatrix = GLMatrixUtils.getIdentityMatrix();
        glColorLayer.onDraw(0xFF4081, identityMatrix);

        // 还原视口
        GLES20.glViewport(0, 0, width, height);
    }
}
