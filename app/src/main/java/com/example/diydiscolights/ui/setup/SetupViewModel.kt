package com.example.diydiscolights.ui.setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetupViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is setup Fragment"
    }
    val text: LiveData<String> = _text
}