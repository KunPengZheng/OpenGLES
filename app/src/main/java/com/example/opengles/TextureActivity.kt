package com.example.opengles

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.opengles.renderer.TextureRenderer

/**
 * Created b Zwp on 2019/6/14.
 */
class TextureActivity : AppCompatActivity() {

    private var glSurfaceView: GLSurfaceView? = null
    private var matrix_1_1Btn: Button? = null
    private var nonMatrixBtn: Button? = null
    private var textureRenderer: TextureRenderer? = null
    private var bitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_texture)

        bitmap = getBitmap()

        textureRenderer = TextureRenderer(this)
        textureRenderer?.setBitmap(bitmap)

        glSurfaceView = findViewById(R.id.gl_view)
        glSurfaceView?.setEGLContextClientVersion(2)
        glSurfaceView?.setRenderer(textureRenderer)
        glSurfaceView?.setOnClickListener(l)
        glSurfaceView?.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

//        nonMatrixBtn = findViewById(R.id.none_matrix)
//        matrix_1_1Btn = findViewById(R.id.texture_matrix_1_1)

//        nonMatrixBtn?.setOnClickListener(l)
//        matrix_1_1Btn?.setOnClickListener(l)
    }

    private val l = View.OnClickListener {
        //        if (it === nonMatrixBtn) {
//            textureRenderer?.setUserSelectDrawType(TextureRenderer.DrawType.NON_TEXTURE_MATRIX)
//            glSurfaceView?.requestRender()
//        }
//
//        if (it === matrix_1_1Btn) {
//            textureRenderer?.setUserSelectDrawType(TextureRenderer.DrawType.TEXTURE_MATRIX_1_1)
//            glSurfaceView?.requestRender()
//        }

        if (it === glSurfaceView) {
            val width = bitmap?.width
            val height = bitmap?.height
            val bmpScale = 1.0f * width!! / height!!
            Toast.makeText(this, "图片_宽:$width，高:$height，比例:$bmpScale", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getBitmap(): Bitmap? {
        return try {
            BitmapFactory.decodeStream(this.resources.assets.open("texture/fengj.png"))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_mvpmatrix, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.MvpMatrix_9_16 -> textureRenderer?.setMvpMatrixType(TextureRenderer.ScaleType.SCALE_9_16)
            R.id.MvpMatrix_4_3 -> textureRenderer?.setMvpMatrixType(TextureRenderer.ScaleType.SCALE_4_3)
            R.id.MvpMatrix_1_1 -> textureRenderer?.setMvpMatrixType(TextureRenderer.ScaleType.SCALE_1_1)
        }
        glSurfaceView?.requestRender()
        return super.onOptionsItemSelected(item)
    }

}
