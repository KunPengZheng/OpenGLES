package com.example.opengles.renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import com.example.opengles.layer.TextureLayer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.io.IOException;

/**
 * Created b Zwp on 2019/6/14.
 */
public class TextureRenderer implements GLSurfaceView.Renderer {

    private final Context context;
    private TextureLayer textureLayer;
    private int width;
    private int height;
    private Bitmap bitmap;

    public TextureRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        try {
            bitmap = BitmapFactory.decodeStream(context.getResources().getAssets().open("texture/fengj.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        textureLayer = new TextureLayer(context, bitmap);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        textureLayer.onDraw(getMvpMatrix(), GLMatrixUtils.getIdentityMatrix());
        textureLayer.onDraw(getMvpMatrix(), getTextureMatrix());
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

    private float[] getTextureMatrix() {
        float[] matrix = new float[16];

        int widthBmp = bitmap.getWidth();
        int heightBmp = bitmap.getHeight();

        if (widthBmp > heightBmp) {
            Matrix.orthoM(matrix, 0,
                    -1.0f * widthBmp / heightBmp,
                    1.0f * widthBmp / heightBmp,
                    -1f, 1f,
                    -1, 1);
        } else if (widthBmp < heightBmp) {
            Matrix.orthoM(matrix, 0,
                    -1f, 1f,
                    -1.0f * heightBmp / widthBmp,
                    1.0f * heightBmp / widthBmp,
                    -1, 1);
        } else {
            Matrix.orthoM(matrix, 0,
                    -1f, 1f,
                    -1f, 1f,
                    -1, 1);
        }
        return matrix;
    }


}
