package com.desafioandroid.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafioandroid.model.Posting
import com.desafioandroid.service.Service
import kotlinx.coroutines.launch

class MainViewModel(private val service: Service): ViewModel() {
    val postingsList = MutableLiveData<List<Posting>>()

    fun getPostingsList() {
        viewModelScope.launch {
            postingsList.value = service.getPostings()
        }
    }
}