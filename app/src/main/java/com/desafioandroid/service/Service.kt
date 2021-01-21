package com.desafioandroid.service

import com.desafioandroid.model.Category
import com.desafioandroid.model.Posting
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Service {

    @GET("lancamentos")
    suspend fun getPostings(
    ): List<Posting>

//    @GET("categorias")
//    suspend fun getCategories(
//    ): List<Category>

    companion object {
        private const val BASE_URL = "https://desafio-it-server.herokuapp.com/"

        fun create(): Service {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Service::class.java)
        }

    }

}



