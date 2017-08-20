package com.example.banty.mygallary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import android.widget.Spinner

class MainActivity : AppCompatActivity() {

    private var gridview: GridView? = null
    private var mainImage: ImageView? = null
    private var directorySpinner: Spinner? = null
    private var closeButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUIElements()
    }

    fun initUIElements() {
        gridview = findViewById<GridView>(R.id.gridview)
        mainImage = findViewById<ImageView>(R.id.main_image)
        directorySpinner = findViewById<Spinner>(R.id.spinner)
        closeButton = findViewById<ImageView>(R.id.iv_close)

        closeButton?.setOnClickListener { view -> finish() }

    }
}
