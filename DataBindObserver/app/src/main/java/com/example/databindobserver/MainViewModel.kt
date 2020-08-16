package com.example.databindobserver

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var editText: MutableLiveData<String> = MutableLiveData()
    var showText: String = ""
}