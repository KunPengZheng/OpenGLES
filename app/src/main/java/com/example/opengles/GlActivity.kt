package com.example.opengles

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.opengles.gl.GLColorLayer
import com.example.opengles.gl.core.GLBaseLayer
import com.example.opengles.gl.utils.GLMatrixUtils
import com.example.opengles.renderer.TriangleRenderer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created b Zwp on 2019/6/13.
 */
class GlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gl)

        val glSurfaceView = findViewById<GLSurfaceView>(R.id.gl_view)
        glSurfaceView.setEGLContextClientVersion(2)
//        glSurfaceView.setRenderer(DemoRenderer(this))
        glSurfaceView.setRenderer(TriangleRenderer())
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }
}



class DemoRenderer(val context: Context) : GLSurfaceView.Renderer {
    var glColorLayer: GLColorLayer? = null
    var width: Int = 0
    var height: Int = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // 成员变量和构造方法的创建方式都还不是gl环境
        // 这些回调方法才是gl环境
        // 一定要放在gl环境初始化，否则创建不成功 ！！！ 提示着色器创建失败
        glColorLayer = GLColorLayer(context)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        this.width = width
        this.height = height
        GLES20.glViewport(0, 0, width, height)
        glColorLayer?.setProjectOrtho(width, height)
    }

    override fun onDrawFrame(gl: GL10?) {

        val identityMatrix = GLMatrixUtils.getIdentityMatrix()
        glColorLayer?.onDraw(0xFF4081, identityMatrix)

        // 还原视口
        GLES20.glViewport(0, 0, width, height)
        glColorLayer?.setProjectOrtho(width, height)
    }

}


private const val VERTEX_SHADER: String = "uniform mat4 uMVPMatrix;\n" +
        "attribute vec4 aPosition;\n" +
        "\n" +
        "void main () {\n" +
        "    gl_Position = uMVPMatrix * aPosition;\n" +
        "}";
private const val FRAGMENT_SHANDER: String = "precision highp float;\n" +

        "uniform vec4 uColor;\n" +
        "\n" +
        "void main () {\n" +
        "    gl_FragColor = uColor;\n" +
        "}";

class DemoLayer constructor(
    vertexCoord: FloatArray, textureCoord: FloatArray,
    vertexShader: String, fragmentShader: String
) : GLBaseLayer(vertexCoord, textureCoord, vertexShader, fragmentShader) {

    var uColorLoc: Int = 0

    override fun getHandle() {
        super.getHandle()
        uColorLoc = GLES20.glGetUniformLocation(mProgramHandle, "uColor")
}


}