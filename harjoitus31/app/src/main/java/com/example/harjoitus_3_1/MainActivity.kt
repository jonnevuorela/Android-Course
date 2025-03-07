package com.example.harjoitus_3_1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences

    lateinit var button1Koti: Button
    lateinit var button2Koti: Button
    lateinit var button3Koti: Button
    lateinit var buttonVirheKoti: Button
    lateinit var virheetKoti: TextView
    lateinit var pisteenKoti: TextView

    lateinit var button1Vieras: Button
    lateinit var button2Vieras: Button
    lateinit var button3Vieras: Button
    lateinit var buttonVirheVieras: Button
    lateinit var virheetVieras: TextView
    lateinit var pisteetVieras: TextView

    fun initUi(){
        button1Koti = findViewById(R.id.button1_koti)
        button2Koti = findViewById(R.id.button2_koti)
        button3Koti = findViewById(R.id.button3_koti)
        buttonVirheKoti = findViewById(R.id.button_virhe_koti)

        button1Vieras = findViewById(R.id.button1_vieras)
        button2Vieras = findViewById(R.id.button2_vieras)
        button3Vieras = findViewById(R.id.button3_vieras)
        buttonVirheVieras = findViewById(R.id.button_virhe_vieras)

        virheetKoti = findViewById(R.id.virheet_koti)
        virheetVieras = findViewById(R.id.virheet_vieras)
        pisteenKoti = findViewById(R.id.pisteet_koti)
        pisteetVieras = findViewById(R.id.pisteet_vieras)
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

        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        initUi()
    }


}