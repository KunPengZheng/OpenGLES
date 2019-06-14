package com.example.opengles

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private var data: ArrayList<MenuBean>? = null
    private var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addActivity()

        recyclerView = findViewById(R.id.recycle_view)
        recyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val menuAdapter = data?.let { MenuAdapter(it) }
        recyclerView?.adapter = menuAdapter

    }

    private fun addActivity() {
        data = ArrayList()

        add("视口或矩阵的绘制", TriangleActivity::class.java)
        add("纹理", TextureActivity::class.java)
    }

    private fun add(name: String, clazz: Class<*>) {
        val bean = MenuBean()
        bean.name = name
        bean.clazz = clazz
        data?.add(bean)
    }

    private inner class MenuAdapter(var data: ArrayList<MenuBean>) : RecyclerView.Adapter<MenuAdapter.MenuHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
            return MenuHolder(getLayoutInflater().inflate(R.layout.item_button, parent, false))
        }

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: MenuHolder, position: Int) {
            holder.position = position
        }

        internal inner class MenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val mBtn: Button

            init {
                mBtn = itemView.findViewById(R.id.mBtn) as Button
                mBtn.setOnClickListener(this@MainActivity)
            }

            fun setPosition(position: Int) {
                val bean = data.get(position)
                mBtn.setText(bean.name)
                mBtn.tag = position
            }
        }
    }

    override fun onClick(view: View?) {
        val position = view?.getTag() as Int
        val bean = data?.get(position)
        val clazz = bean?.clazz
        startActivity(Intent(this, clazz))
    }

}


private class MenuBean {
    internal var name: String? = null
    internal var clazz: Class<*>? = null
}


