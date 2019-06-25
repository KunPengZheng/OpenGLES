package com.example.opengles.renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import com.example.opengles.gl.utils.GLMatrixUtils;
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
    private int type = DrawType.NON_TEXTURE_MATRIX;

    public TextureRenderer(Context context) {
        this.context = context;
    }

    public void setUserSelectDrawType(@DrawType int type) {
        this.type = type;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f, 0f, 0f, 1f);

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
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        if (type == DrawType.NON_TEXTURE_MATRIX) {
            textureLayer.onDraw(getMvpMatrix(), GLMatrixUtils.getIdentityMatrix());
            return;
        }
        if (type == DrawType.TEXTURE_MATRIX_1_1) {
            textureLayer.onDraw(getMvpMatrix(), getTextureMatrix());
            return;
        }
    }

    // 9：16顶点坐标矩阵
    // 要求图片：1：1显示，4：3显示，9：16显示
    // 假设图片是9：16（实际宽高可能大于显示区，也可能小于显示区），则在1：1要求下，看你要截图中间，还是要以那一边进行缩放
    //
    private float[] getMvpMatrix() {
        float[] matrix = new float[16];
        Matrix.orthoM(matrix, 0,
                -1f, 1f,
                -1.0f * height / width,
                1.0f * height / width,
                -1, 1);

        return matrix;
    }

//    private float[] getMvpMatrix(@ScaleType int scale) {
//        switch (scale) {
//            case ScaleType.SCALE_4_3:
//                break;
//            case ScaleType.SCALE_1_1:
//                break;
//            case ScaleType.SCALE_9_16:
//                break;
//            default:
//                break;
//        }
//    }

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

    public @interface DrawType {
        int NON_TEXTURE_MATRIX = 0;
        int TEXTURE_MATRIX_1_1 = 1;
    }

    /**
     * 宽高比
     */
    public @interface ScaleType {
        int SCALE_1_1 = 0;
        int SCALE_4_3 = 1;
        int SCALE_9_16 = 2;
    }
}
