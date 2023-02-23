package com.houseshare.presentation.cleaning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.houseshare.domain.cleaning.Cleaning
import com.houseshare.domain.cleaning.CleaningRepository

class CleaningViewModel(
    private val cleaningRepository: CleaningRepository = CleaningRepository()
) : ViewModel() {

    private val _cleaningList = MutableLiveData(cleaningRepository.getCleaningList())
    val cleaningList: LiveData<List<Cleaning>> = _cleaningList

}