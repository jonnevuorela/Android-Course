package com.example.harjoitus_3_1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences

    var virheetKotiKey: String = "virheet_koti"
    var pisteetKotiKey: String = "pisteet_koti"
    lateinit var virheetKoti: TextView
    lateinit var pisteetKoti: TextView

    var virheetVierasKey: String = "virheet_vieras"
    var pisteetVierasKey: String = "pisteet_vieras"
    lateinit var virheetVieras: TextView
    lateinit var pisteetVieras: TextView

    fun initUi(){
        virheetKoti = findViewById(R.id.virheet_koti)
        virheetVieras = findViewById(R.id.virheet_vieras)
        pisteetKoti = findViewById(R.id.pisteet_koti)
        pisteetVieras = findViewById(R.id.pisteet_vieras)

        virheetKoti.text = sharedPref.getInt(virheetKotiKey, 0).toString()
        virheetVieras.text = sharedPref.getInt(virheetVierasKey, 0).toString()
        pisteetKoti.text = sharedPref.getInt(pisteetKotiKey, 0).toString()
        pisteetVieras.text = sharedPref.getInt(pisteetVierasKey, 0).toString()
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

    // Virhe laskut
    fun addVirheVieras(view: View){
        val editor = sharedPref.edit()
        val virheet = virheetVieras.text.toString().toInt() + 1

        virheetVieras.text = virheet.toString()
        editor.putInt(virheetVierasKey, virheet)
        editor.apply()
    }
    fun reduceVirheVieras(view: View){
        val editor = sharedPref.edit()
        val virheet = virheetVieras.text.toString().toInt() - 1

        virheetVieras.text = virheet.toString()
        editor.putInt(virheetVierasKey, virheet)
        editor.apply()
    }
    fun addVirheKoti(view: View){
        val editor = sharedPref.edit()
        val virheet = virheetKoti.text.toString().toInt() + 1

        virheetKoti.text = virheet.toString()
        editor.putInt(virheetKotiKey, virheet)
        editor.apply()
    }
    fun reduceVirheKoti(view: View){
        val editor = sharedPref.edit()
        val virheet = virheetKoti.text.toString().toInt() - 1

        virheetKoti.text = virheet.toString()
        editor.putInt(virheetKotiKey, virheet)
        editor.apply()
    }

    // Piste laskut
    fun pisteetKoti(view: View){
        val editor = sharedPref.edit()
        var pisteet = pisteetKoti.text.toString().toInt()
        when (view.id){
            R.id.pisteet_koti -> pisteet-=1
            R.id.button1_koti -> pisteet+=1
            R.id.button2_koti -> pisteet+=2
            R.id.button3_koti -> pisteet+=3
        }
        pisteetKoti.text = pisteet.toString()
        editor.putInt(pisteetKotiKey, pisteet)
        editor.apply()

    }
    fun pisteetVieras(view: View){
        val editor = sharedPref.edit()
        var pisteet = pisteetVieras.text.toString().toInt()
        when (view.id){
            R.id.pisteet_vieras -> pisteet-=1
            R.id.button1_vieras -> pisteet+=1
            R.id.button2_vieras -> pisteet+=2
            R.id.button3_vieras -> pisteet+=3
        }
        pisteetVieras.text = pisteet.toString()
        editor.putInt(pisteetVierasKey, pisteet)
        editor.apply()

    }

}