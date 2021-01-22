package com.desafioandroid.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafioandroid.model.Posting
import com.desafioandroid.service.Service
import kotlinx.coroutines.launch

class MainViewModel(private val service: Service): ViewModel() {
    val postingsList = MutableLiveData<MutableList<Posting>>()

    fun getPostingsList() {

        viewModelScope.launch {
            var posting = service.getPostings()
            val category = service.getCategories()
            posting.forEach { post->
                post.categoria = category.find {
                    it.id == post.categoria
                }?.nome?:"not found"
            }
            postingsList.value = posting
        }
    }
}