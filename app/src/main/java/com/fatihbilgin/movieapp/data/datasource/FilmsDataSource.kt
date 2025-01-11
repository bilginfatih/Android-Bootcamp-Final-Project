package com.fatihbilgin.movieapp.data.datasource

import android.util.Log
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.data.entity.MovieCartData
import com.fatihbilgin.movieapp.retrofit.FilmsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmsDataSource(var filmsDao: FilmsDao) {

    suspend fun filmsGet(): List<FilmsData> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = filmsDao.getFilms()
            response.movies
        } catch (e: Exception) {
            Log.e("FilmsDataSource", "Error fetching films: ${e.message}")
            emptyList()
        }
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
                       userName: String) {
        filmsDao.insert(name, image, price, category, rating, year, director, description, orderAmount, userName)
    }

    suspend fun movieCartGet(userName: String) : List<MovieCartData> = withContext(Dispatchers.IO) {
        return@withContext filmsDao.getMovieCart(userName).movie_cart
    }

    suspend fun deleteMovie(cartId: Int, userName: String) {
        Log.e("Movie Sil", cartId.toString())
        filmsDao.deleteMovie(cartId, userName)
    }

}