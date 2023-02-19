package com.houseshare.presentation.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.houseshare.domain.shopping.Shopping

class ShoppingViewModel: ViewModel() {

    private val _testList: MutableLiveData<List<Shopping>> = MutableLiveData(listOf(
        Shopping("sium"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("sium"),
        Shopping("sium"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
        Shopping("fratm"),
    ))
    val testList: LiveData<List<Shopping>> = _testList

}