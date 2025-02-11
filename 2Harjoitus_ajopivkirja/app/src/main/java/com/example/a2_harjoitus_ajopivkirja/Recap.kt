package com.example.a2_harjoitus_ajopivkirja

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Recap : AppCompatActivity() {
    lateinit var vehicle_recap: TextView
    lateinit var travel_recap: TextView
    lateinit var menu_button: Button

    companion object {
        private var selectedVehicle: String = ""
        private var travelDistance: String = ""

        fun setRecapData(vehicle: String, distance: String) {
            selectedVehicle = vehicle
            travelDistance = distance
        }

        fun initializeElements(activity: Recap) {
            activity.vehicle_recap = activity.findViewById(R.id.vehicle_recap)
            activity.travel_recap = activity.findViewById(R.id.travel_recap)
            activity.menu_button = activity.findViewById(R.id.menu)

            activity.vehicle_recap.text = selectedVehicle
            activity.travel_recap.text = travelDistance

       }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recap)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeElements(this)
        menu_button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}