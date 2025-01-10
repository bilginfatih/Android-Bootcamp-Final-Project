package com.fatihbilgin.movieapp.retrofit

import com.fatihbilgin.movieapp.data.entity.CRUDResponse
import com.fatihbilgin.movieapp.data.entity.FilmsResponse
import com.fatihbilgin.movieapp.data.entity.MovieCartResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FilmsDao {
    // Dao: Database Access Object
    // http://kasimadalan.pe.hu/movies/getAllMovies.php
    // http://kasimadalan.pe.hu/ -> base url
    // movies/getAllMovies.php -> api url
    @GET("movies/getAllMovies.php")
    suspend fun getFilms() : FilmsResponse

    @POST("movies/insertMovie.php")
    @FormUrlEncoded
    suspend fun insert(@Field("name") name: String,
                       @Field("image") image: String,
                       @Field("price") price: Int,
                       @Field("category") category: String,
                       @Field("rating") rating: Double,
                       @Field("year") year: Int,
                       @Field("director") director: String,
                       @Field("description") description: String,
                       @Field("orderAmount") orderAmount: Int,
                       @Field("userName") userName: String,) : CRUDResponse

    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun getMovieCart(@Field("userName") userName: String) : MovieCartResponse

    @POST("movies/deleteMovie.php")
    @FormUrlEncoded
    suspend fun deleteMovie(@Field("cartId") cartId: Int, @Field("userName") userName: String) : CRUDResponse

}