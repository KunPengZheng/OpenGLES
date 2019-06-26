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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.opengles.renderer.TextureRenderer


/**
 * Created b Zwp on 2019/6/14.
 */
class TextureActivity : AppCompatActivity() {

    private var glSurfaceView: GLSurfaceView? = null
    private var nonMatrixBtn: Button? = null
    private var matrix_4_3Btn: Button? = null
    private var matrix_1_1Btn: Button? = null
    private var matrix_9_16Btn: Button? = null
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

//        nonMatrixBtn = findViewById(R.id.texture_matrix_identity)
//        matrix_1_1Btn = findViewById(R.id.texture_matrix_1_1)
//        matrix_4_3Btn = findViewById(R.id.texture_matrix_1_1)
//        matrix_9_16Btn = findViewById(R.id.texture_matrix_1_1)

        nonMatrixBtn?.setOnClickListener(l)
        matrix_1_1Btn?.setOnClickListener(l)
        matrix_4_3Btn?.setOnClickListener(l)
        matrix_9_16Btn?.setOnClickListener(l)
    }

    private val l = View.OnClickListener {
        if (it === nonMatrixBtn) {
            textureRenderer?.setTextureMatrixType(TextureRenderer.TextureScaleType.IDENTITY)
            glSurfaceView?.requestRender()
        }

        if (it === matrix_1_1Btn) {
            textureRenderer?.setTextureMatrixType(TextureRenderer.TextureScaleType.SCALE_1_1)
            glSurfaceView?.requestRender()
        }

        if (it === matrix_4_3Btn) {
            textureRenderer?.setTextureMatrixType(TextureRenderer.TextureScaleType.SCALE_4_3)
            glSurfaceView?.requestRender()
        }

        if (it === matrix_9_16Btn) {
            textureRenderer?.setTextureMatrixType(TextureRenderer.TextureScaleType.SCALE_9_16)
            glSurfaceView?.requestRender()
        }

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
            R.id.texture_matrix -> {
                showTextureDialog()
                return super.onOptionsItemSelected(item)
            }
            R.id.mvp_matrix_9_16 -> textureRenderer?.setMvpMatrixType(TextureRenderer.ScaleType.SCALE_9_16)
            R.id.mvp_matrix_4_3 -> textureRenderer?.setMvpMatrixType(TextureRenderer.ScaleType.SCALE_4_3)
            R.id.mvp_matrix_1_1 -> textureRenderer?.setMvpMatrixType(TextureRenderer.ScaleType.SCALE_1_1)

        }
        glSurfaceView?.requestRender()
        return super.onOptionsItemSelected(item)
    }

    private fun showTextureDialog() {

//        val arrayOf = arrayOf("原始（无）", "1:1", "4:3", "9:16")
//        val type = arrayOf(
//            TextureRenderer.TextureScaleType.IDENTITY,
//            TextureRenderer.TextureScaleType.SCALE_1_1,
//            TextureRenderer.TextureScaleType.SCALE_4_3,
//            TextureRenderer.TextureScaleType.SCALE_9_16
//        )

        val hashMap = HashMap<String, Int>()
        hashMap.put("原始（无）", TextureRenderer.TextureScaleType.IDENTITY)
        hashMap.put("1:1", TextureRenderer.TextureScaleType.SCALE_1_1)
        hashMap.put("4:3", TextureRenderer.TextureScaleType.SCALE_4_3)
        hashMap.put("9:16", TextureRenderer.TextureScaleType.SCALE_9_16)
        val keys = hashMap.keys
        val toList = keys.toList()
        val toTypedArray = toList.toTypedArray()


        val builder = AlertDialog.Builder(this)
        builder.setTitle("纹理矩阵单选框")
            .setSingleChoiceItems(toTypedArray, -1) { dialog, which ->

                val type:Int = hashMap[toTypedArray[which]]!!
                textureRenderer?.setTextureMatrixType(type/*TextureRenderer.TextureScaleType.IDENTITY*/)
                glSurfaceView?.requestRender()
            }
            .setNegativeButton("取消", null)
            .create()
            .show()
    }

    private fun switchTextureMatrixType() {

    }


}
