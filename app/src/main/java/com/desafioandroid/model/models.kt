package com.desafioandroid.model

data class Posting(
    val id: Int,
    val valor: Double,
    val origem: String,
    val categoria: Int,
    val mes_lancamento: Int
)

data class Category(
    val id: Int,
    val nome: String
)