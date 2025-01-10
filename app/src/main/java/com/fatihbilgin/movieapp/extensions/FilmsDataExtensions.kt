package com.fatihbilgin.movieapp.extensions

import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.data.entity.MovieCartData

fun FilmsData.imageUrl(): String {
    val baseUrl = "http://kasimadalan.pe.hu/movies/images/"
    return baseUrl + this.image
}

fun MovieCartData.imageUrl(): String {
    val baseUrl = "http://kasimadalan.pe.hu/movies/images/"
    return baseUrl + this.image
}
