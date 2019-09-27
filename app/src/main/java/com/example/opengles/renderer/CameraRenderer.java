package com.example.opengles.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import com.example.opengles.gl.core.GLCoordBuffer;
import com.example.opengles.gl.utils.FileUtils;
import com.example.opengles.layer.CameraLayer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created b Zwp on 2019/7/2.
 */
public class CameraRenderer implements GLSurfaceView.Renderer {

    private final Context context;
    private CameraLayer cameraLayer;

    public CameraRenderer(Context context) {
        this.context = context;
    }



    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        String vertexCoord = FileUtils.assets2String(context, "shader/oes_base_vertex.sh");
        String textureCoord = FileUtils.assets2String(context, "shader/oes_base_fragment.sh");
        cameraLayer = new CameraLayer(GLCoordBuffer.DEFAULT_VERTEX_COORDINATE,
                GLCoordBuffer.DEFAULT_FLIP_Y_TEXTURE_COORDINATE, vertexCoord, textureCoord);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }


}
