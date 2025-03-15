package com.example.matikkapeli2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

enum class Operator(val symbol: String) {
    MINUS("-"),
    PLUS("+");
}

data class Game(
    var gameId: Int = 0,
    var operator: Operator = Operator.MINUS,
    var score: Int = 0,
    var time: Int = 0
)

class GameViewModel: ViewModel() {
    var game = Game()

    private lateinit var sharedPrefs: SharedPreferences

    private var _numbers1: MutableList<Int>? = null
    private var _numbers2: MutableList<Int>? = null
    private var _currentRound = MutableLiveData<Int>(0)
    private var _score = MutableLiveData<Int>(0)

    private var timerJob: Job? = null
    private var _startTimeEpoch = MutableLiveData<Long>(0)
    private val _formattedTime = MutableLiveData<String>("00:00")
    val formattedTime: LiveData<String> = _formattedTime
    private var _isTimerRunning = false
    fun initialize(context: Context) {
        sharedPrefs = context.getSharedPreferences("games_data", Context.MODE_PRIVATE)
    }

    fun createNewGame(operator: Operator = Operator.MINUS, score: Int = 0, time: Int = 0): Game {
        val nextId = getNextGameId()
        return Game(gameId = nextId, operator = operator, score = score, time = time)
    }

    private fun getNextGameId(): Int {
        val games = getAllSavedGames()
        return if (games.isEmpty()) {
            1  // default
        } else {
            (games.maxByOrNull { it.gameId }?.gameId ?: 0) + 1
        }
    }
    fun saveGameToPrefs(game: Game) {
        val gson = Gson()
        val jsonString = gson.toJson(game)
        sharedPrefs.edit().putString("game_${game.gameId}", jsonString).apply()
    }

    fun loadGame(gameId: Int): Game? {
        val jsonString = sharedPrefs.getString("game_$gameId", null) ?: return null
        return try {
            Gson().fromJson(jsonString, Game::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun getAllSavedGames(): List<Game> {
        val games = mutableListOf<Game>()

        sharedPrefs.all.keys.filter { it.startsWith("game_") }.forEach { key ->
            sharedPrefs.getString(key, null)?.let { jsonString ->
                try {
                    games.add(Gson().fromJson(jsonString, Game::class.java))
                } catch (e: Exception) {
                }
            }
        }

        return games
    }

    // tekoäly generoitu timer logiikka
    fun startTimer() {
        if (!_isTimerRunning) {
            if (_startTimeEpoch.value == 0L) {
                _startTimeEpoch.value = System.currentTimeMillis() / 1000
            }

            timerJob = viewModelScope.launch {
                while (isActive) {
                    val currentTimeEpoch = System.currentTimeMillis() / 1000
                    val elapsedSeconds = (currentTimeEpoch - (_startTimeEpoch.value ?: 0)).toInt()
                    game.time = elapsedSeconds
                    val minutes = elapsedSeconds / 60
                    val seconds = elapsedSeconds % 60
                    val timeString = String.format("%02d:%02d", minutes, seconds)

                    println("Timer updated: $timeString")

                    withContext(Dispatchers.Main) {
                        _formattedTime.value = timeString
                    }

                    delay(1000)
                }
            }

            _isTimerRunning = true
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        _isTimerRunning = false
    }
    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
    fun getNumbers1(): MutableList<Int> {
        if (_numbers1 == null) {
            _numbers1 = MutableList(10) { Random.nextInt(0, 10) }
        }
        return _numbers1!!
    }

    fun getNumbers2(): MutableList<Int> {
        if (_numbers2 == null) {
            _numbers2 = MutableList(10) { Random.nextInt(0, 10) }
        }
        return _numbers2!!
    }

    fun updateCurrentRound(round: Int) {
        _currentRound.value = round
    }

    fun incrementScore() {
        _score.value = (_score.value ?: 0) + 1
    }

    fun getCurrentRound(): Int {
        return _currentRound.value ?: 0
    }

    fun getScore(): Int {
        return _score.value ?: 0
    }

    fun resetGameData() {
        _numbers1 = null
        _numbers2 = null
        _currentRound.value = 0
        _score.value = 0
        stopTimer()
        _startTimeEpoch.value = 0
        _formattedTime.value = "00:00"
        game = createNewGame(game.operator)
    }

}
class MainActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences
    lateinit var appBarConfiguration: AppBarConfiguration
    val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        gameViewModel.initialize(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController


        val topLevelDestination = setOf(
            R.id.mainmenuFragment,
        )

        appBarConfiguration = AppBarConfiguration(topLevelDestination)
        setupActionBarWithNavController(navController, appBarConfiguration)



    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }
    }