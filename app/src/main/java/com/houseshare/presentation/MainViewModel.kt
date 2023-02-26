package com.houseshare.presentation

import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.houseshare.domain.navigation.NavigationRepository
import com.houseshare.domain.navigation.toDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigationRepository: NavigationRepository
) : ViewModel() {


    private val _test = MutableLiveData<Int>()
    val test: LiveData<Int> = _test

    private val _lastTopLevelDestination = MutableLiveData<Int>()
    val lastTopLevelDestination: LiveData<Int> get() = _lastTopLevelDestination

    fun updateStartingDestination(@IdRes destinationId: Int) {
        viewModelScope.launch {
            val destination = destinationId.toDestination() ?: throw Exception("Invalid destination!")
            navigationRepository.updateDestination(destination)
        }
    }

    @IdRes
    fun fetchStartingDestination(): Int {
        // TODO: This is just a test, later with the splash screen this will be implemented with launch
        return runBlocking {
            navigationRepository.fetchInitialNavigation().destination.toId()
        }
    }

}