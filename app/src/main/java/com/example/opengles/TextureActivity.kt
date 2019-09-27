package com.example.opengles

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.opengles.renderer.TextureRenderer
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * Created b Zwp on 2019/6/14.
 */
class TextureActivity : AppCompatActivity() {

    private var bitmap: Bitmap? = null
    private var glSurfaceView: GLSurfaceView? = null
    private var textureRenderer: TextureRenderer? = null
    private var floatingActionButton: FloatingActionButton? = null


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

        floatingActionButton = findViewById(R.id.fab_rotate)
        floatingActionButton?.setOnClickListener(l)
    }

    private val l = View.OnClickListener {

        if (it === glSurfaceView) {
            val width = bitmap?.width
            val height = bitmap?.height
            val bmpScale = 1.0f * width!! / height!!
            Toast.makeText(this, "图片_宽:$width，高:$height，比例:$bmpScale", Toast.LENGTH_SHORT).show()
        }

        if (it === floatingActionButton) {
            showRotateDialog()
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
        menuInflater.inflate(R.menu.menu_matrix, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.texture_matrix -> {
                showTextureDialog()
                return super.onOptionsItemSelected(item)
            }
            R.id.mvp_matrix_9_16 -> textureRenderer?.setMvpMatrixType(TextureRenderer.MvpScaleType.SCALE_9_16)
            R.id.mvp_matrix_4_3 -> textureRenderer?.setMvpMatrixType(TextureRenderer.MvpScaleType.SCALE_4_3)
            R.id.mvp_matrix_1_1 -> textureRenderer?.setMvpMatrixType(TextureRenderer.MvpScaleType.SCALE_1_1)

        }
        glSurfaceView?.requestRender()
        return super.onOptionsItemSelected(item)
    }

    private fun showTextureDialog() {

        val hashMap = HashMap<String, Int>()
        hashMap["原始（无）"] = TextureRenderer.TextureScaleType.IDENTITY
        hashMap["1:1"] = TextureRenderer.TextureScaleType.SCALE_1_1
        hashMap["4:3"] = TextureRenderer.TextureScaleType.SCALE_4_3
        hashMap["9:16"] = TextureRenderer.TextureScaleType.SCALE_9_16
        val keys = hashMap.keys
        val toList = keys.toList()
        val toTypedArray = toList.toTypedArray()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("纹理矩阵单选框")
            .setSingleChoiceItems(toTypedArray, -1) { dialog, which ->

                val type: Int = hashMap[toTypedArray[which]]!!
                textureRenderer?.setTextureMatrixType(type)
                glSurfaceView?.requestRender()
            }
            .setNegativeButton("取消", null)
            .create()
            .show()
    }

    private fun showRotateDialog() {
        val hashMap = HashMap<String, Int>()
        hashMap["原始（无）"] = TextureRenderer.MvpRotateType.IDENTITY
        hashMap["绕z轴顺时针旋转90°"] = TextureRenderer.MvpRotateType.ROTATE_CW_Z_90
        hashMap["绕z轴逆时针旋转90°"] = TextureRenderer.MvpRotateType.ROTATE_CCW_Z_90
        val keys = hashMap.keys
        val toList = keys.toList()
        val toTypedArray = toList.toTypedArray()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("纹理矩阵单选框")
            .setSingleChoiceItems(toTypedArray, -1) { dialog, which ->
                dialog.dismiss()

                val type: Int = hashMap[toTypedArray[which]]!!
                textureRenderer?.setMvpRotateType(type)
                glSurfaceView?.requestRender()

            }
            .setNegativeButton("取消", null)
            .create()
            .show()
    }

}
