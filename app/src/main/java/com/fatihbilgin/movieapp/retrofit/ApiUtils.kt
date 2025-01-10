package com.fatihbilgin.movieapp.retrofit

class ApiUtils {
    companion object {
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getFilmsDao() : FilmsDao {
            return RetrofitClient.getClient(BASE_URL).create(FilmsDao::class.java)
        }
    }
}