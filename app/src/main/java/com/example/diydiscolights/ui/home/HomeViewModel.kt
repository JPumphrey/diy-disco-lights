package com.example.diydiscolights.ui.home

import androidx.lifecycle.*

class HomeViewModel : ViewModel() {

    val flashing = MutableLiveData<Boolean>().apply {
        value = false
    }

    val bpm = MutableLiveData<Int>().apply {
        value = 120
    }
}