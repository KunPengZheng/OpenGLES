package com.example.opengles

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.opengles.renderer.TriangleRenderer

/**
 * Created b Zwp on 2019/6/13.
 */
class GlActivity : AppCompatActivity() {
    var viewPortBtn: Button? = null
    var matrixBtn: Button? = null
    var glSurfaceView: GLSurfaceView? = null
    var triangleRenderer: TriangleRenderer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gl)

        triangleRenderer = TriangleRenderer()

        glSurfaceView = findViewById(R.id.gl_view)
        glSurfaceView?.setEGLContextClientVersion(2)
        glSurfaceView?.setRenderer(triangleRenderer)
        glSurfaceView?.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        viewPortBtn = findViewById(R.id.btn_view_port_type)
        matrixBtn = findViewById(R.id.btn_matrix_type)

        viewPortBtn?.setOnClickListener(l)
        matrixBtn?.setOnClickListener(l)
    }

    private val l: View.OnClickListener = View.OnClickListener {
        if (it === viewPortBtn) {
            triangleRenderer?.setDrawType(false)
            glSurfaceView?.requestRender()
        }

        if (it === matrixBtn) {
            triangleRenderer?.setDrawType(true)
            glSurfaceView?.requestRender()
        }
    }

}

