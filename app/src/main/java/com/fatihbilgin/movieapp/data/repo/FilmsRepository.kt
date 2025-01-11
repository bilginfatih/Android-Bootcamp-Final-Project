package com.fatihbilgin.movieapp.data.repo

import android.util.Log
import com.fatihbilgin.movieapp.data.datasource.FilmsDataSource
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.data.entity.MovieCartData

class FilmsRepository(var filmsDataSource: FilmsDataSource) {

    suspend fun filmsGet(): List<FilmsData> {
        val films = filmsDataSource.filmsGet()
        Log.d("LogFatih: FilmsRepository", "Fetched films from data source: $films")
        return films
    }
    suspend fun addMovie(name: String,
                       image: String,
                       price: Int,
                       category: String,
                       rating: Double,
                       year: Int,
                       director: String,
                       description: String,
                       orderAmount: Int,
                       userName: String) = filmsDataSource.addMovie(name, image, price, category, rating, year, director, description, orderAmount, userName)

    suspend fun movieCartGet(userName: String) : List<MovieCartData> = filmsDataSource.movieCartGet(userName)

    suspend fun deleteMovie(cartId: Int, userName: String) = filmsDataSource.deleteMovie(cartId, userName)
}