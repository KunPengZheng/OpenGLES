package com.example.opengles.gl.utils;

import android.opengl.Matrix;

/**
 * @author Sykent.Lao e-mail:sykent.lao@gmail.com blog:https://sykent.github.io/
 * @version 1.0
 * @since 2019/04/22
 */
public class GLMatrixUtils {

    /**
     * 旋转
     *
     * @param m     目标矩阵
     * @param angle 旋转角度
     * @return m
     */
    public static float[] rotate(float[] m, float angle) {
        Matrix.rotateM(m, 0, angle, 0, 0, 1);
        return m;
    }

    /**
     * 翻转
     *
     * @param m 目标矩阵
     * @param x x轴是否翻转。true则-翻转1，false则1不变
     * @param y y轴是否翻转。1不变。true则-翻转1，false则1不变
     * @return m
     */
    public static float[] flip(float[] m, boolean x, boolean y) {
        if (x || y) {
            Matrix.scaleM(m, 0, x ? -1 : 1, y ? -1 : 1, 1);
        }
        return m;
    }

    /**
     * 缩放
     *
     * @param m 目标矩阵
     * @param x x轴缩放。(0,1)缩小，1不变，>1放大；[-1,0)翻转且放大，-1翻转且不变，<-1缩小
     * @param y y轴缩放。(0,1)缩小，1不变，>1放大；[-1,0)翻转且放大，-1翻转且不变，<-1缩小
     * @return m
     */
    public static float[] scale(float[] m, float x, float y) {
        Matrix.scaleM(m, 0, x, y, 1);
        return m;
    }

    public static float[] getIdentityMatrix() {
        float[] identityMatrix = new float[16];
        Matrix.setIdentityM(identityMatrix, 0);
        return identityMatrix;
    }
}
