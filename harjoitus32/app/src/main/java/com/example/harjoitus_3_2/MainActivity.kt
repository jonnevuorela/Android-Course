package com.example.harjoitus_3_2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences

    lateinit var dice1: ImageView
    lateinit var dice2: ImageView


    fun initUi(){
        dice1 = findViewById(R.id.dice1)
        dice2 = findViewById(R.id.dice2)
        updateDice()
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

    fun roll(view: View){
        val number1: Int = Random.nextInt(0,6)
        val number2: Int = Random.nextInt(0,6)
        val editor = sharedPref.edit()

        editor.putInt("dice1", number1)
        editor.putInt("dice2", number2)
        editor.apply()
        updateDice()
    }

    fun updateDice(){
        val drawable1Index = sharedPref.getInt("dice1", 0)
        val drawable2Index = sharedPref.getInt("dice2", 0)
        val dice1Drawable = DiceDrawable.fromIndex(drawable1Index)
        val dice2Drawable = DiceDrawable.fromIndex(drawable2Index)
        dice1.setImageDrawable(ContextCompat.getDrawable(this, dice1Drawable.drawableResId))
        dice2.setImageDrawable(ContextCompat.getDrawable(this, dice2Drawable.drawableResId))
    }
}

enum class DiceDrawable(val drawableResId: Int) {
    dice_1(R.drawable.dice_1),
    dice_2(R.drawable.dice_2),
    dice_3(R.drawable.dice_3),
    dice_4(R.drawable.dice_4),
    dice_5(R.drawable.dice_5),
    dice_6(R.drawable.dice_6);

    companion object {
        fun fromIndex(index: Int): DiceDrawable {
            return values()[index]
        }
    }
}