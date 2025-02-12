package com.example.matikkapeli

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry

class MainViewModel : ViewModel(), Observable {
    private val callbacks = PropertyChangeRegistry()

    private val _currentAnswerText = MutableLiveData<String>()
    val currentAnswerText: LiveData<String> = _currentAnswerText

    val answerTextContent = MutableLiveData<String>()

    val points: String = "4"
    val number1: String = "1"
    val number2: String = "2"

    fun sendAnswer() {
        val answer = answerTextContent.value
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }
}
