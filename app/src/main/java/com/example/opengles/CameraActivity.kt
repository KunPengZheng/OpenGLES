package com.example.opengles

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.opengles.camera.CameraView
import com.example.opengles.utils.PermissionUtils


/**
 * Created b Zwp on 2019/6/14.
 */
class CameraActivity : AppCompatActivity() {

    private var mCameraView: CameraView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val arrayOf = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        PermissionUtils.askPermission(this, arrayOf, 10, initViewRunnable)
    }


    private val initViewRunnable = Runnable {
        setContentView(R.layout.activity_camera)
        mCameraView = findViewById(R.id.camera_view)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.onRequestPermissionsResult(requestCode == 10, grantResults, initViewRunnable,
            Runnable {
                Toast.makeText(this@CameraActivity, "没有获得必要的权限", Toast.LENGTH_SHORT).show()
                finish()
            })
    }


    override fun onResume() {
        super.onResume()
        mCameraView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mCameraView?.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("切换摄像头").setTitle("切换摄像头").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val name = item.title.toString()
        if (name == "切换摄像头") {
            mCameraView?.switchCamera()
        }
        return super.onOptionsItemSelected(item)
    }
}
