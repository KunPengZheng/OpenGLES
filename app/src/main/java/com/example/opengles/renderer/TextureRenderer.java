package com.example.opengles.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import com.example.opengles.gl.utils.GLMatrixUtils;
import com.example.opengles.layer.TextureLayer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created b Zwp on 2019/6/14.
 */
public class TextureRenderer implements GLSurfaceView.Renderer {

    private final Context context;
    private TextureLayer textureLayer;
    private int width;
    private int height;

    public TextureRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        textureLayer = new TextureLayer(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        textureLayer.onDraw(getMvpMatrix(), GLMatrixUtils.getIdentityMatrix());
    }

    private float[] getMvpMatrix() {
        float[] matrix = new float[16];
        Matrix.orthoM(matrix, 0,
                -1f, 1f,
                -1.0f * height / width,
                1.0f * height / width,
                -1, 1);

        return matrix;
    }
}
