package com.example.opengles

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val findViewById = findViewById<TextView>(R.id.tv_view)
        findViewById.setOnClickListener { xx()}
    }

    private fun xx() {
        startActivity(Intent(this, GlActivity::class.java))
    }
}

