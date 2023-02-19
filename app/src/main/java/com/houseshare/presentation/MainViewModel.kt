package com.houseshare.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {


    private val _test = MutableLiveData<Int>()
    val test : LiveData<Int> = _test



}