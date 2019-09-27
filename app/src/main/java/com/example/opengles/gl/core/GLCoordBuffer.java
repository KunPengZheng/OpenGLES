package com.example.opengles.gl.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * 顶点和纹理的坐标Buffer数据Bean
 */
public class GLCoordBuffer {
    /**
     * 顶点坐标数据缓冲
     */
    private FloatBuffer mVertexCoorBuffer;

    /**
     * 纹理坐标数据缓冲
     */
    private FloatBuffer mTexCoordBuffer;

    /**
     * 顶点个数
     */
    private int mVertexCount;

    /**
     * 一个点用几个分量表示（x,y）
     */
    private int mCoordsPerVertex;

    /**
     * 纹理坐标中一个点的字节偏移量
     */
    private int mVertexStride;

    /**
     * 顶点坐标中一个点的字节偏移量
     */
    private int mTexCoordStride;

    public GLCoordBuffer() {

    }

    /**
     * 生成顶点坐标和纹理坐标的数据缓冲
     *
     * @param vertexCoord  顶点坐标数组
     * @param textureCoord 纹理坐标数组
     */
    public GLCoordBuffer(float[] vertexCoord, float[] textureCoord) {
        mVertexCoorBuffer = generateFloatBuffer(vertexCoord);
        mTexCoordBuffer = generateFloatBuffer(textureCoord);

        mCoordsPerVertex = 2; // 一次取多少个，（x,y）一般是两个分量表示一个点
        mVertexStride = mCoordsPerVertex * SIZE_OF_FLOAT; // 顶点坐标一次取多少字节
        mVertexCount = vertexCoord.length / mCoordsPerVertex; // 顶点个数
        mTexCoordStride = mCoordsPerVertex * SIZE_OF_FLOAT; // 纹理坐标一次取多少字节
    }

    /**
     * 生成顶点坐标的数据缓冲
     *
     * @param vertexCoord
     */
    public void generateVertexFloatBuffer(float[] vertexCoord) {
        mCoordsPerVertex = 2; // 一次取多少个，（x,y）一般是两个分量表示一个点
        mVertexCoorBuffer = generateFloatBuffer(vertexCoord);
        mVertexStride = mCoordsPerVertex * SIZE_OF_FLOAT; // 顶点坐标一次取多少字节
        mVertexCount = vertexCoord.length / mCoordsPerVertex; // 顶点个数
    }

    /**
     * 生成纹理坐标的数据缓冲
     *
     * @param textureCoord
     */
    public void generateTexCoordFloatBuffer(float[] textureCoord) {
        mCoordsPerVertex = 2; // 一次取多少个，（x,y）一般是两个分量表示
        mTexCoordBuffer = generateFloatBuffer(textureCoord);
        mTexCoordStride = mCoordsPerVertex * SIZE_OF_FLOAT; // 纹理坐标一次取多少字节
    }

    public FloatBuffer getVertexBuffer() {
        return mVertexCoorBuffer;
    }

    public FloatBuffer getTexCoordBuffer() {
        return mTexCoordBuffer;
    }

    /**
     * Returns the number of vertices stored in the vertex array.
     */
    public int getVertexCount() {
        return mVertexCount;
    }

    /**
     * Returns the width, in bytes, of the data for each vertex.
     */
    public int getVertexStride() {
        return mVertexStride;
    }

    /**
     * Returns the width, in bytes, of the data for each texture coordinate.
     */
    public int getTexCoordStride() {
        return mTexCoordStride;
    }

    /**
     * Returns the number of position coordinates per vertex.  This will be 2 or 3.
     */
    public int getCoordsPerVertex() {
        return mCoordsPerVertex;
    }

    public FloatBuffer generateFloatBuffer(float[] floatArr) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(floatArr.length * 4) // 创建数据缓存区
                .order(ByteOrder.nativeOrder())                         // 设置字节顺序
                .asFloatBuffer()                                        // 转换为Float 型缓冲
                .put(floatArr);                                        // 缓冲区填入数据
        buffer.position(0);                                           // 设置缓存区起点位置

        return buffer;
    }

    private static final int SIZE_OF_FLOAT = 4; // float类型32位，四个字节

    /**
     * 铺满视口的默认顶点坐标
     * （x,y）二维坐标系，取值范围[0,1]
     */
    public static final float[] DEFAULT_VERTEX_COORDINATE = {
            -1.0f, -1.0f,        // 0 left bottom
            1.0f, -1.0f,         // 1 right bottom
            -1.0f, 1.0f,          // 2 left top
            1.0f, 1.0f            // 3 right top
    };

    /**
     * 铺满视口默认的纹理坐标
     * （x,y）二维坐标系，取值范围[0,1]
     */
    public static final float[] DEFAULT_TEXTURE_COORDINATE = {
            0.0f, 0.0f,            // 0 left bottom
            1.0f, 0.0f,            // 1 right bottom
            0.0f, 1.0f,            // 2 left top
            1.0f, 1.0f,            // 3 right top
    };

    /**
     * 铺满视口默认上下翻转的纹理坐标
     * （x,y）二维坐标系，取值范围[0,1]
     */
    public static final float[] DEFAULT_FLIP_Y_TEXTURE_COORDINATE = {
            0.0f, 1.0f,            // 0 left top
            1.0f, 1.0f,            // 1 right top
            0.0f, 0.0f,            // 2 left bottom
            1.0f, 0.0f,            // 3 right bottom
    };
}
