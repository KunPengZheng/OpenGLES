package com.example.opengles.renderer;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.opengles.gl.core.GLCoordBuffer;
import com.example.opengles.gl.utils.GLMatrixUtils;
import com.example.opengles.gl.utils.GLUtilsEx;
import com.example.opengles.layer.TriangleLayer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * Created b Zwp on 2019/6/13.
 */
public class TriangleRenderer implements GLSurfaceView.Renderer {

    private TriangleLayer triangleLayer;
    private int width;
    private int height;
    private ArrayList<Point> viewPortList;
    private int unifiedDstViewPortWidth;
    private int unifiedDstViewPortHeight;
    private ArrayList<PointF> translationXYList;
    private boolean drawTypeFlag;

    public void setDrawType(boolean drawTypeFlag) {
        this.drawTypeFlag = drawTypeFlag;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        triangleLayer = new TriangleLayer();
        GLUtilsEx.glClearColor();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glViewport(0, 0, width, height);
        getViewPortArrs(width, height);
        getTranslationXY();
        unifiedDstViewPortWidth = (int) (width / 2f);
        unifiedDstViewPortHeight = (int) (height / 2f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        GLUtilsEx.glClear();

        if (drawTypeFlag) {
            viewPortDraw();
        } else {
            matrixDraw();
        }
    }

    private void bg() {
        GLCoordBuffer coordBuffer = new GLCoordBuffer();
        FloatBuffer floatBuffer = coordBuffer.generateFloatBuffer(GLCoordBuffer.DEFAULT_VERTEX_COORDINATE);

    }

    private void matrixDraw() {
        int[] colorArr = new int[]{0xFF4081, 0x008577, Color.YELLOW};
        for (int i = 0; i < viewPortList.size(); i++) {
            float[] mvpMatrix2 = getMvpMatrixOnMatrixDrawType();
            PointF pointF = translationXYList.get(i);
            Matrix.translateM(mvpMatrix2, 0, pointF.x, pointF.y, 1);
            GLMatrixUtils.scale(mvpMatrix2, 0.5f, 0.5f);
            triangleLayer.onDraw(colorArr[i], mvpMatrix2);
        }
        GLES20.glViewport(0, 0, width, height);
    }


    private void viewPortDraw() {
        int[] colorArr = new int[]{Color.RED, Color.GREEN, Color.BLUE};
        for (int i = 0; i < viewPortList.size(); i++) {
            Point point = viewPortList.get(i);
            int x = point.x;
            int y = point.y;
            float[] mvpMatrix = getMvpMatrixOnViewPortDrawType();
            GLES20.glViewport(x, y, unifiedDstViewPortWidth, unifiedDstViewPortHeight);
            triangleLayer.onDraw(colorArr[i], mvpMatrix);
        }
        GLES20.glViewport(0, 0, width, height);
    }

    private void getTranslationXY() {
        translationXYList = new ArrayList<>();
        PointF point = new PointF(-0.5f, -0.5f);
        translationXYList.add(point);
        PointF point1 = new PointF(0.5f, 0.5f);
        translationXYList.add(point1);
        PointF point2 = new PointF(0.5f, -0.5f);
        translationXYList.add(point2);
    }

    private void getViewPortArrs(int width, int height) {
        // GLES20.glViewport(0, 0, width, height);
        // 指定视口的x,y。
        viewPortList = new ArrayList<>();
        Point point = new Point(0, 0);
        viewPortList.add(point);
        Point point1 = new Point((int) (width / 2f), (int) (height / 2f));
        viewPortList.add(point1);
        Point point2 = new Point((int) (width / 2f), 0);
        viewPortList.add(point2);
    }

    private float[] getMvpMatrixOnViewPortDrawType() {
        // 默认竖屏，最短边为基准
        // 视口的width，height固定为glsurfaceview的四分之一
        float[] matrix = new float[16];
        Matrix.orthoM(matrix, 0,
                -1f, 1f,
                -1.0f * unifiedDstViewPortHeight / unifiedDstViewPortWidth,
                1.0f * unifiedDstViewPortHeight / unifiedDstViewPortWidth,
                -1, 1);

        return matrix;
    }

    private float[] getMvpMatrixOnMatrixDrawType() {
        float[] matrix = new float[16];
        Matrix.orthoM(matrix, 0,
                -1f, 1f,
                -1.0f * height / width,
                1.0f * height / width,
                -1, 1);

        return matrix;
    }

}
