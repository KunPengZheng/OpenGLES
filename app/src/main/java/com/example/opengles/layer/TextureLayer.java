package com.example.opengles.layer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.opengles.gl.core.GLBaseLayer;
import com.example.opengles.gl.core.GLCoordBuffer;
import com.example.opengles.gl.utils.GLUtilsEx;

import java.io.IOException;

/**
 * Created b Zwp on 2019/6/14.
 */
public class TextureLayer extends GLBaseLayer {

    private final Context context;

    public TextureLayer(Context context) {
        super(GLCoordBuffer.DEFAULT_VERTEX_COORDINATE,
                GLCoordBuffer.DEFAULT_TEXTURE_COORDINATE,
                VERTEX_SHADER, FRAGMENT_SHANDER);
        this.context = context;
    }

    public void onDraw(float[] mvpMatrix, float[] texMatrix) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(context.getResources().getAssets().open("texture/fengj.png"));
            if (bitmap != null && !bitmap.isRecycled()) {
                int texture = GLUtilsEx.createTexture(bitmap);
                super.onDraw(texture, mvpMatrix, texMatrix);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
