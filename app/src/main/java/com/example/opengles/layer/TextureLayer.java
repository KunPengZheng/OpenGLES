package com.example.opengles.layer;

import android.content.Context;
import android.graphics.Bitmap;
import com.example.opengles.gl.core.GLBaseLayer;
import com.example.opengles.gl.core.GLCoordBuffer;
import com.example.opengles.gl.utils.GLUtilsEx;

/**
 * Created b Zwp on 2019/6/14.
 */
public class TextureLayer extends GLBaseLayer {

    private final Context context;
    private Bitmap bitmap;

    public TextureLayer(Context context, Bitmap bitmap) {
        // gl中图片的坐标是向下，所以默认读取后的图片在实际看来是翻转的，所以要经过翻转才能显示成实际的视觉。
        super(GLCoordBuffer.DEFAULT_VERTEX_COORDINATE, GLCoordBuffer.DEFAULT_FLIP_Y_TEXTURE_COORDINATE,
                VERTEX_SHADER, FRAGMENT_SHANDER);
        this.context = context;
        this.bitmap = bitmap;
    }

    public void onDraw(float[] mvpMatrix, float[] texMatrix) {
        if (bitmap != null && !bitmap.isRecycled()) {
            int texture = GLUtilsEx.createTexture(bitmap);
            super.onDraw(texture, mvpMatrix, texMatrix);
        }
    }


    private static final String VERTEX_SHADER = "uniform mat4 uMVPMatrix;\n" +
            "uniform mat4 uTexMatrix;\n" +
            "attribute vec4 aPosition;\n" +
            "attribute vec4 aTextureCoord;\n" +
            "varying vec2 vTextureCoord;\n" +
            "\n" +
            "void main () {\n" +
            "    gl_Position = uMVPMatrix * aPosition;\n" +
            "    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n" +
            "}";
    private static final String FRAGMENT_SHANDER = "precision highp float;\n" +
            "uniform sampler2D sourceImage;\n" +
            "varying vec2 vTextureCoord;\n" +
            "\n" +
            "void main () {\n" +
            "    vec4 color = texture2D(sourceImage, vTextureCoord);\n" +
            "    gl_FragColor = color;\n" +
            "}";

}
