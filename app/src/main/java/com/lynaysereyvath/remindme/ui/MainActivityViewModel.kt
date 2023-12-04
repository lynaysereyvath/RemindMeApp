package com.lynaysereyvath.remindme.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    var name: String by mutableStateOf("")
        private set
    var quote: String by mutableStateOf("")
        private set


}