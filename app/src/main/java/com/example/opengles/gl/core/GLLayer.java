package com.example.opengles.gl.core;

import android.opengl.GLES20;

/**
 * 流程接口
 */
public interface GLLayer {

    /**
     * 设置正交矩阵所需要的视口宽高
     * 默认以视口的最短边为基准[-1,1]，然后高边为[-1.0f * height / width, 1.0f * height / width]
     *
     * @param width  视口宽
     * @param height 视口高
     */
    void setProjectOrtho(int width, int height);

    /**
     * 顶点坐标和纹理坐标的原始数据
     * 顶点坐标：在视口内指定显示的区域，坐标系和数学坐标系一致（原点（0，0）在视口的中心点，一般设置范围为x[-1,1],y[-1,1],体现归一化）
     * 纹理坐标：指定图片要显示的区域（相当于抠哪部分图来显示），y的坐标系向下和android屏幕坐标系一致（取值范围x[0,1],y[0,1]）
     *
     * @param vertexCoord  顶点坐标原始数据
     * @param textureCoord 纹理坐标原始数据
     */
    void initGlCoordinateBuffer(final float[] vertexCoord, final float[] textureCoord);

    /**
     * 初始化着色器
     *
     * @param vertexShader   顶点着色器
     * @param fragmentShader 纹理着色器
     */
    void initShader(final String vertexShader, final String fragmentShader);

    /**
     * 绘制之前会回调
     */
    void preDraw();

    /**
     * 生成纹理
     *
     * @param target    纹理类型
     * @param textureId 纹理地址
     */
    void onEnableTexture(int target, int textureId);

    /**
     * 获取顶点着色器和片元着色器所需变量的句柄
     */
    void getHandle();

    /**
     * 绑定顶点坐标矩阵和纹理坐标矩阵
     *
     * @param mvpMatrix 顶点坐标矩阵
     * @param texMatrix 纹理坐标矩阵
     */
    void enableHandle(float[] mvpMatrix, float[] texMatrix);

    /**
     * 绘制
     *
     * @param textureId 纹理地址
     * @param mvpMatrix 顶点坐标矩阵
     * @param texMatrix 纹理坐标矩阵
     */
    void onDraw(final int textureId, float[] mvpMatrix, float[] texMatrix);

    /**
     * 绘制
     *
     * @param textureId     纹理地址
     * @param mvpMatrix     顶点坐标矩阵
     * @param texMatrix     纹理坐标矩阵
     * @param glCoordBuffer 顶点坐标或者纹理坐标的Buffer数据的封装体
     */
    void onDraw(final int textureId, float[] mvpMatrix, float[] texMatrix, GLCoordBuffer glCoordBuffer);

    /**
     * 绘制
     *
     * @param textureId     纹理地址
     * @param mvpMatrix     顶点坐标矩阵
     * @param texMatrix     纹理坐标矩阵
     * @param glCoordBuffer 顶点坐标或者纹理坐标的Buffer数据的封装体
     * @param drawMode      绘制模式  {@linkplain GLES20#glDrawArrays(int, int, int) 参数1}
     */
    void onDraw(final int textureId, float[] mvpMatrix, float[] texMatrix, GLCoordBuffer glCoordBuffer, int drawMode);

    /**
     * 禁止句柄。一般禁止顶点坐标句柄和纹理坐标句柄
     */
    void disableHandle();

    /**
     * 解绑纹理
     *
     * @param target 纹理类型
     */
    void onUnbindTexture(int target);

    /**
     * 销毁
     */
    void destroy();
}
