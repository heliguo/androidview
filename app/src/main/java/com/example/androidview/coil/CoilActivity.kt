package com.example.androidview.coil

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import coil.load
import com.example.androidview.BaseActivity
import com.example.androidview.R

/**
 *@author lgh on 2021/4/2 17:36
 *@description 图片加载库 coil kotlin
 */
class CoilActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coil)
        val iv = findViewById<ImageView>(R.id.coil_iv)
        val btn = findViewById<Button>(R.id.coil_btn)
        btn.setOnClickListener {
            iv.load(R.drawable.ccdzz)
        }
    }

}