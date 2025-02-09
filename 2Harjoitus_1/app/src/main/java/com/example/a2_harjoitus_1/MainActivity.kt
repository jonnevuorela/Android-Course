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

class MainActivity : AppCompatActivity() {

    lateinit var counter: TextView
    lateinit var counterStr: String
    lateinit var add: Button
    lateinit var decrease: Button
    lateinit var reset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        SwipeManager.initialize(this, Activity2::class.java, Activity2::class.java)


        add = findViewById(R.id.add)
        decrease = findViewById(R.id.decrease)
        reset = findViewById(R.id.reset)
        counter = findViewById(R.id.counter)
        counterStr = counter.text.toString()

        reset.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                counter.setText(0.toString())
            }
        })

        add.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var counterInt = counterStr.toInt() + 1
                counterStr = counterInt.toString()
                counter.setText(counterStr)
            }
        })

        decrease.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var counterInt = counterStr.toInt() - 1
                counterStr = counterInt.toString()
                counter.setText(counterStr)
            }
        })
    }

}
