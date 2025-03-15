package com.example.matikkapeli2

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PeliFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PeliFragment: Fragment() {
    private val gameViewModel: GameViewModel by activityViewModels()
    private lateinit var timerTextView: TextView
    private lateinit var number1: TextView
    private lateinit var number2: TextView
    private lateinit var operator: TextView
    private lateinit var answerInput: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_peli, container, false)
        timerTextView = view.findViewById(R.id.time)
        number1 = view.findViewById(R.id.number1)
        number2 = view.findViewById(R.id.number2)
        operator = view.findViewById(R.id.operator)
        answerInput = view.findViewById(R.id.input)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        operator.text = gameViewModel.game.operator.symbol

        // enter key listener
        answerInput.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                processAnswer()
                return@setOnEditorActionListener true
            }
            false
        }

        // enter key listener
        answerInput.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                processAnswer()
                return@setOnKeyListener true
            }
            false
        }

        gameViewModel.formattedTime.observe(viewLifecycleOwner) { formattedTime ->
            timerTextView.text = formattedTime
        }

        gameViewModel.startTimer()

        showCurrentRound()
    }

    private fun showCurrentRound() {
        val currentRound = gameViewModel.getCurrentRound()

        if (currentRound < 10) {
            val num1 = gameViewModel.getNumbers1()[currentRound]
            val num2 = gameViewModel.getNumbers2()[currentRound]

            number1.text = num1.toString()
            number2.text = num2.toString()
            answerInput.text.clear()
            answerInput.requestFocus()
        } else {
            finishGame()
        }
    }

    private fun processAnswer() {
        val userAnswer = answerInput.text.toString().toIntOrNull()

        if (userAnswer != null) {
            val currentRound = gameViewModel.getCurrentRound()

            val num1 = gameViewModel.getNumbers1()[currentRound]
            val num2 = gameViewModel.getNumbers2()[currentRound]

            val correctAnswer = when (gameViewModel.game.operator) {
                Operator.MINUS -> num1 - num2
                Operator.PLUS -> num1 + num2
            }

            if (userAnswer == correctAnswer) {
                gameViewModel.incrementScore()
            }

            gameViewModel.updateCurrentRound(currentRound + 1)

            showCurrentRound()
        }
    }

    private fun finishGame() {
        gameViewModel.stopTimer()
        gameViewModel.game.score = gameViewModel.getScore()
        gameViewModel.saveGameToPrefs(gameViewModel.game)
        findNavController().navigate(R.id.action_peli_to_recap)
    }
}