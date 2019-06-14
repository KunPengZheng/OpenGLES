package com.example.opengles.layer;

import android.graphics.Color;
import android.opengl.GLES20;
import com.example.opengles.gl.core.GLBaseLayer;
import com.example.opengles.gl.core.GLCoordBuffer;
import com.example.opengles.gl.utils.GLMatrixUtils;
import com.example.opengles.gl.utils.GLUtilsEx;

/**
 * Created b Zwp on 2019/6/13.
 */
public class TriangleLayer extends GLBaseLayer {

    private int uColorLoc;
    private float[] mColor;

    public TriangleLayer() {
        super(triangleCoords, GLCoordBuffer.DEFAULT_TEXTURE_COORDINATE,
                vertexShaderCode, fragmentShaderCode);
    }

    @Override
    public void getHandle() {
        aPositionLoc = GLES20.glGetAttribLocation(mProgramHandle, "vPosition");
        uMVPMatrixLoc = GLES20.glGetUniformLocation(mProgramHandle, "vMatrix");
        uColorLoc = GLES20.glGetUniformLocation(mProgramHandle, "vColor");
    }

    @Override
    public void enableHandle(float[] mvpMatrix, float[] texMatrix) {
        super.enableHandle(mvpMatrix, texMatrix);
        GLES20.glUniform4fv(uColorLoc, 1, mColor, 0);
    }

    public void onDraw(int color, float[] mvpMatrix) {
        setColor(color);
        super.onDraw(GLUtilsEx.NO_TEXTURE, mvpMatrix, GLMatrixUtils.getIdentityMatrix());
    }

    private void setColor(int color) {
        if (mColor == null || mColor.length == 0) {
            mColor = new float[4];
        }
        mColor[0] = Color.red(color) / 255f;
        mColor[1] = Color.green(color) / 255f;
        mColor[2] = Color.blue(color) / 255f;
        mColor[3] = Color.alpha(color) / 255f;
    }


    private static final float[] triangleCoords = {
//            1.5f, 1.5f, // top
//            -1.5f, -1.5f, // bottom left
//            1.5f, -1.5f  // bottom right
            1.0f, 1.0f, // top
            -1.0f, -1.0f, // bottom left
            1.0f, -1.0f  // bottom right
    };


    private static final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 vMatrix;" +
                    "void main() {" +
                    "  gl_Position = vMatrix*vPosition;" +
                    "}";

    private static final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

}
