package com.desafioandroid.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desafioandroid.model.Posting
import com.desafioandroid.service.Service
import kotlinx.coroutines.launch

class MainViewModel(private val service: Service) : ViewModel() {
    val postingsList = MutableLiveData<MutableList<Posting>>()

    fun getPostingsList(mes: Int? = null) {

        //Populando a lista de lancamentos
        viewModelScope.launch {
            var posting = service.getPostings()
            val category = service.getCategories()

            //Mudando id da categoria para nome da categoria
            posting.forEach { post ->
                post.categoria = category.find {
                    it.id == post.categoria
                }?.nome ?: "not found"
            }
            postingsList.value = posting

            //Filtrando por mes
            if (mes != null) {
                var monthList = mutableListOf<Posting>()
                for (i in 0..(postingsList.value?.size?.minus(1) ?: 0)) {
                    if (postingsList.value?.get(i)?.mes_lancamento ?: 1 == mes) {
                        postingsList.value?.get(i)?.let { monthList.add(it) }
                    }
                }
                postingsList.value = monthList
            }
        }
    }
}