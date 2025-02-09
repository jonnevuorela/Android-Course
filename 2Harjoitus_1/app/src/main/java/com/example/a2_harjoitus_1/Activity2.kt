package com.example.a2_harjoitus_1

import SwipeManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity2 : AppCompatActivity() {

    private lateinit var raffle1: TextView
    private lateinit var raffle2: TextView
    private lateinit var raffle3: TextView
    private lateinit var raffle1Str: String
    private lateinit var raffle2Str: String
    private lateinit var raffle3Str: String
    private lateinit var raffleButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        SwipeManager.initialize(this, MainActivity::class.java, MainActivity::class.java)

        raffle1 = findViewById(R.id.raffle1)
        raffle2 = findViewById(R.id.raffle2)
        raffle3 = findViewById(R.id.raffle3)

        raffle1Str = raffle1.text.toString()
        raffle2Str = raffle2.text.toString()
        raffle3Str = raffle3.text.toString()

        raffleButton = findViewById(R.id.raffle)

        raffleButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                while (true){
                var raffle1Int = (0..10).random()
                var raffle2Int = (0..10).random()
                var raffle3Int = (0..10).random()

                raffle1Str = raffle1Int.toString()
                    if (raffle2Int != raffle1Int){
                        raffle2Str = raffle2Int.toString()
                    }
                    if (raffle3Int != raffle1Int && raffle3Int != raffle2Int){
                        raffle3Str = raffle3Int.toString()
                    }
                    if (raffle1Str != "" && raffle2Str != "" && raffle3Str != ""){
                        break
                    }
                }

                raffle1.setText(raffle1Str)
                raffle2.setText(raffle2Str)
                raffle3.setText(raffle3Str)
            }

        })
    }

}