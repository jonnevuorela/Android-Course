package com.example.harjoitus_3_2

import android.content.Context
import android.content.SharedPreferences
import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import android.os.Bundle
import android.preference.Preference
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences

    var gameInstanceKey: String = "game_instance"
    var gameInstance: GameInstance? = null

    lateinit var dice1: ImageView
    lateinit var dice2: ImageView

    lateinit var rollBtn: Button
    lateinit var startBtn: Button
    lateinit var scoreText: TextView


    fun initUi(){
        dice1 = findViewById(R.id.dice1)
        dice2 = findViewById(R.id.dice2)
        rollBtn = findViewById(R.id.roll)
        startBtn = findViewById(R.id.start)
        scoreText = findViewById(R.id.pisteet)
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

        val gameInstanceJson = sharedPref.getString(gameInstanceKey, null)
        initUi()
        if (gameInstanceJson != null) {
            try {
                val jsonObject = JSONObject(gameInstanceJson)
                gameInstance = GameInstance(
                    score = jsonObject.getInt("score"),
                    rollsLeft = jsonObject.getInt("rollsLeft"),
                    isGameOver = jsonObject.getBoolean("isGameOver")
                )
            } catch (e: Exception) {
                Toast.makeText(this, "virhe pelitietojen hakemisessa, alustetaan uusi peli.", Toast.LENGTH_LONG).show()
            }
        }
        refresh()
    }
    fun refresh() {
        if (gameInstance != null) {
            gameInstance?.let {
                scoreText.text = "${it.score}"

                rollBtn.isEnabled = !it.isGameOver && it.rollsLeft > 0
                startBtn.isEnabled = it.isGameOver || it.rollsLeft <= 0
            }
        } else {
            scoreText.text = "0"
            rollBtn.isEnabled = false
            startBtn.isEnabled = true
        }

        updateDice()
    }
    fun saveGameInstance(){
        val editor = sharedPref.edit()
        val gameInstanceJson = JSONObject()
        gameInstance?.let {
            gameInstanceJson.put("score", it.score)
            gameInstanceJson.put("rollsLeft", it.rollsLeft)
            gameInstanceJson.put("isGameOver", it.isGameOver)
        }

        editor.putString(gameInstanceKey, gameInstanceJson.toString())
        editor.apply()
    }
    fun newGameInstance(view: View){
        gameInstance = GameInstance(
            score = 0,
            rollsLeft = 5,
            isGameOver = false,
        )
        saveGameInstance()
        refresh()

    }

    fun roll(view: View){
        gameInstance?.let {
            if (it.rollsLeft > 0 && !it.isGameOver) {
                val number1: Int = Random.nextInt(0, 6)
                val number2: Int = Random.nextInt(0, 6)
                val editor = sharedPref.edit()

                editor.putInt("dice1", number1)
                editor.putInt("dice2", number2)
                editor.apply()

                it.score += (number1 + number2 + 2)
                it.rollsLeft -= 1

                if (it.rollsLeft <= 0) {
                    it.isGameOver = true

                }
                updateDice()
                saveGameInstance()
                refresh()
            }
        }


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

data class GameInstance(
    var score: Int,
    var rollsLeft: Int,
    var isGameOver: Boolean,
)

enum class DiceDrawable(val drawableResId: Int) {
    dice_1(R.drawable.dice_1),
    dice_2(R.drawable.dice_2),
    dice_3(R.drawable.dice_3),
    dice_4(R.drawable.dice_4),
    dice_5(R.drawable.dice_5),
    dice_6(R.drawable.dice_6);

    companion object {
        fun fromIndex(index: Int): DiceDrawable {
            return entries[index]
        }
    }
}