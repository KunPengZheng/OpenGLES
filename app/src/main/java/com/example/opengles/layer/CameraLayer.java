package com.example.opengles.layer;

import android.opengl.GLES20;
import com.example.opengles.gl.core.GLBaseLayer;

/**
 * Created b Zwp on 2019/7/2.
 */
public class CameraLayer extends GLBaseLayer {

    public CameraLayer(float[] vertexCoord, float[] textureCoord, String vertexShader, String fragmentShader) {
        super(vertexCoord, textureCoord, vertexShader, fragmentShader);
    }

    @Override
    public void getHandle() {
        aPositionLoc = GLES20.glGetAttribLocation(mProgramHandle, "vPosition");
        aTextureCoordLoc = GLES20.glGetAttribLocation(mProgramHandle, "vCoord");
        uMVPMatrixLoc = GLES20.glGetUniformLocation(mProgramHandle, "vMatrix");
        mTextureLoc = GLES20.glGetUniformLocation(mProgramHandle, "vTexture");
    }

    public void onDraw() {
//        super.onDraw();
    }


}
