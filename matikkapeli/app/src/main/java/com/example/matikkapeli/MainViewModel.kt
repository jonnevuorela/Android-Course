package com.example.matikkapeli

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import kotlin.random.Random

class MainViewModel : ViewModel(), Observable {
    val callbacks = PropertyChangeRegistry()

    val navigateToSummary = MutableLiveData<Boolean>()

    val answerTextContent = MutableLiveData<String>()
    val snackbarMessage = MutableLiveData<String>()

    val points = MutableLiveData(0)

    val number1 = MutableLiveData(0)

    val number2 = MutableLiveData(0)

    val exercise = MutableLiveData(0)

    val numberOfExercises = 5


    fun initializeNumbers() {
        points.value = 0
        exercise.value = 1
        randomize()
    }

    private fun randomize() {
        number1.value = Random.nextInt(1, 11)
        number2.value = Random.nextInt(1, 11)
    }

    private fun nextExercise() {
        if (exercise.value == numberOfExercises) {
            navigateToSummary.value = true
        }else{
            answerTextContent.value = ""
            randomize()
            exercise.value = (exercise.value ?: 0) + 1
        }
    }

    fun sendAnswer() {
        try {
            val answer = Integer.parseInt(answerTextContent.value ?: "")
            if (answer == number1.value?.plus(number2.value ?: 0)) {
                snackbarMessage.value = "Correct!"
                points.value = (points.value ?: 0) + 1
                nextExercise()
            } else {
                snackbarMessage.value = "Wrong answer..."
                nextExercise()
            }
        } catch (e: NumberFormatException) {
            snackbarMessage.value = "Invalid number format"
        }
    }

    fun snackbarMessageHandled() {
        snackbarMessage.value = ""
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }
}