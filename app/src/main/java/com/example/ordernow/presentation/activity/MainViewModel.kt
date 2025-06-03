package com.example.ordernow.presentation.activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    private val _splashScreenVisible = mutableStateOf(value = false)
    val splashScreenVisible : State<Boolean> = _splashScreenVisible

    init {
        viewModelScope.launch {
            _splashScreenVisible.value = true
            delay(1500)
            _splashScreenVisible.value = false
        }
    }

}