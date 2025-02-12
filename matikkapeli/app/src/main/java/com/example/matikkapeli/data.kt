package com.example.matikkapeli

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object data {
    private val _currentAnswerText = MutableLiveData<String>()
    val currentAnswerText: LiveData<String>
        get() = _currentAnswerText

    init {
        _currentAnswerText.value = ""
    }
    fun checkAnswer(){

    }
}