package com.example.opengles

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.opengles.renderer.TextureRenderer

/**
 * Created b Zwp on 2019/6/14.
 */
class TextureActivity : AppCompatActivity() {
    var glSurfaceView: GLSurfaceView? = null
    var matrix_1_1Btn: Button? = null
    var nonMatrixBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_texture)

        var triangleRenderer = TextureRenderer(this)

        glSurfaceView = findViewById(R.id.gl_view)
        glSurfaceView?.setEGLContextClientVersion(2)
        glSurfaceView?.setRenderer(triangleRenderer)
        glSurfaceView?.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        nonMatrixBtn = findViewById(R.id.none_matrix)
        matrix_1_1Btn = findViewById(R.id.texture_matrix_1_1)

        nonMatrixBtn?.setOnClickListener(l)
        matrix_1_1Btn?.setOnClickListener(l)
    }

    val l = View.OnClickListener {
        if (it === nonMatrixBtn) {

        }

        if (it === nonMatrixBtn) {

        }
    }
}
