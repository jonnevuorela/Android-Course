package com.example.a2_harjoitus_ajopivkirja

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var raport_button: Button
    lateinit var add_button: Button
    lateinit var info_button: Button
    lateinit var settings_button: Button

    fun initializeElememnts(){
        raport_button = findViewById(R.id.raport)
        add_button = findViewById(R.id.add)
        info_button = findViewById(R.id.info)
        settings_button = findViewById(R.id.settings)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeElememnts()
        activity.startActivity
    }
    fun navigateToActivityk
}