package com.desafioandroid.model

import java.io.Serializable

data class Posting(
    val id: Int,
    val valor: Double,
    val origem: String,
    var categoria: String,
    val mes_lancamento: Int
): Serializable

data class Category(
    val id: String,
    val nome: String
)