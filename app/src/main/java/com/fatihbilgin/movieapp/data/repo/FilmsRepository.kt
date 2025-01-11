package com.fatihbilgin.movieapp.data.repo

import android.util.Log
import com.fatihbilgin.movieapp.data.datasource.FilmsDataSource
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.data.entity.MovieCartData

class FilmsRepository(var filmsDataSource: FilmsDataSource) {

    // Filmleri veritabanından getirir
    suspend fun filmsGet(): List<FilmsData> {
        val films = filmsDataSource.filmsGet() // Veritabanından filmleri al
        Log.d("LogFatih: FilmsRepository", "Fetched films from data source: $films") // Loglama yap
        return films // Filmleri döndür
    }

    // Yeni bir film ekler
    suspend fun addMovie(
        name: String,
        image: String,
        price: Int,
        category: String,
        rating: Double,
        year: Int,
        director: String,
        description: String,
        orderAmount: Int,
        userName: String
    ) = filmsDataSource.addMovie(name, image, price, category, rating, year, director, description, orderAmount, userName)

    // Kullanıcıya ait film sepetini getirir
    suspend fun movieCartGet(userName: String): List<MovieCartData> = filmsDataSource.movieCartGet(userName)

    // Sepetten bir filmi siler
    suspend fun deleteMovie(cartId: Int, userName: String) = filmsDataSource.deleteMovie(cartId, userName)
}
