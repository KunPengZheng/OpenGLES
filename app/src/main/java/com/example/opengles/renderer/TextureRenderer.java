package com.example.opengles.renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import com.example.opengles.gl.core.MatrixState;
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
    private Bitmap bitmap;

    private int textureMatrixType = TextureScaleType.IDENTITY;
    private int mvpMatrixType = ScaleType.SCALE_1_1;

    public TextureRenderer(Context context) {
        this.context = context;
    }

    public void setBitmap(Bitmap bmp) {
        bitmap = bmp;
    }

    public void setMvpMatrixType(@ScaleType int scale) {
        mvpMatrixType = scale;
    }

    public void setTextureMatrixType(@TextureScaleType int type) {
        this.textureMatrixType = type;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f, 0f, 0f, 1f);
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
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        textureLayer.onDraw(getMvpMatrix(mvpMatrixType), getTextureMatrix(textureMatrixType)/*GLMatrixUtils.getIdentityMatrix()*/);
    }

    private float[] getMvpMatrix(@ScaleType int scale) {

        MatrixState matrixState = new MatrixState();
        matrixState.setProjectOrtho(-1, 1, -1.0f * height / width, 1.0f * height / width, -1, 1);

        switch (scale) {
            case ScaleType.SCALE_4_3:
                matrixState.scale(1f, 3f / 4f, 1f);
                break;

            case ScaleType.SCALE_1_1:
                matrixState.scale(1f, 1f / 1f, 1f);
                break;

            case ScaleType.SCALE_9_16:
                matrixState.scale(1f, 16f / 9f, 1f);
                break;

            default:
                break;
        }

        return matrixState.getMVPMatrix();
    }

    private float[] getTextureMatrix(@TextureScaleType int scale) {
//        float[] matrix = new float[16];
//
//        int widthBmp = bitmap.getWidth();
//        int heightBmp = bitmap.getHeight();
//
//        if (widthBmp > heightBmp) {
//            Matrix.orthoM(matrix, 0,
//                    -1.0f * widthBmp / heightBmp,
//                    1.0f * widthBmp / heightBmp,
//                    -1f, 1f,
//                    -1, 1);
//        } else if (widthBmp < heightBmp) {
//            Matrix.orthoM(matrix, 0,
//                    -1f, 1f,
//                    -1.0f * heightBmp / widthBmp,
//                    1.0f * heightBmp / widthBmp,
//                    -1, 1);
//        } else {
//            Matrix.orthoM(matrix, 0,
//                    -1f, 1f,
//                    -1f, 1f,
//                    -1, 1);
//        }
//        return matrix;

        int widthBmp = bitmap.getWidth();
        int heightBmp = bitmap.getHeight();

        MatrixState matrixState = new MatrixState();
        matrixState.setProjectOrtho(-1, 1, -1.0f * heightBmp / widthBmp, 1.0f * heightBmp / widthBmp, -1, 1);

        switch (scale) {

            case TextureScaleType.IDENTITY:
                return GLMatrixUtils.getIdentityMatrix();

            case TextureScaleType.SCALE_4_3:
                matrixState.scale(1f, 3f / 4f, 1f);
                break;

            case TextureScaleType.SCALE_1_1:
                matrixState.scale(1f, 1f / 1f, 1f);
                break;

            case TextureScaleType.SCALE_9_16:
                matrixState.scale(1f, 16f / 9f, 1f);
                break;

            default:
                break;
        }

        return matrixState.getMVPMatrix();
    }

    /**
     * 宽高比
     */
    public @interface ScaleType {
        int SCALE_1_1 = 0;
        int SCALE_4_3 = 1;
        int SCALE_9_16 = 2;
    }

    /**
     * 宽高比
     */
    public @interface TextureScaleType {
        int IDENTITY = 0;
        int SCALE_1_1 = 1;
        int SCALE_4_3 = 2;
        int SCALE_9_16 = 3;
    }
}
