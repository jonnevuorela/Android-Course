package com.example.a2_harjoitus_ajopivkirja

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class Add : AppCompatActivity() {
    lateinit var vehicle_select: Spinner
    lateinit var travel_edit: TextInputEditText
    lateinit var save_button: Button
    fun initializeElements(){
        vehicle_select = findViewById(R.id.vehicle_select)
        travel_edit = findViewById(R.id.travel_edit)
        save_button = findViewById(R.id.save)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeElements()
        save_button.setOnClickListener {
            val selectedVehicle = vehicle_select.selectedItem.toString()
            val travelDistance = travel_edit.text.toString()

            Recap.setRecapData(selectedVehicle, travelDistance)

            startActivity(Intent(this, Recap::class.java))
        }
    }
}