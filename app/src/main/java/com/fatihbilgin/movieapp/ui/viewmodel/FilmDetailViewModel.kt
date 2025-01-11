package com.fatihbilgin.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatihbilgin.movieapp.data.repo.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmDetailViewModel @Inject constructor(private val filmsRepository: FilmsRepository) : ViewModel() {
    // Sepetteki ürün sayısını tutar
    var _cartItemCount = MutableLiveData(0)

    // Sepetteki ürün sayısını artırır
    fun incrementCartItemCount() {
        _cartItemCount.value = (_cartItemCount.value ?: 0) + 1
    }

    /**
     * Yeni bir film kaydı eklemek için kullanılan fonksiyon.
     * Bu işlem, kullanıcı tarafından bir film sepete eklendiğinde çağrılır.
     */
    fun insert(name: String,
               image: String,
               price: Int,
               category: String,
               rating: Double,
               year: Int,
               director: String,
               description: String,
               orderAmount: Int,
               userName: String) {
        CoroutineScope(Dispatchers.Main).launch {
        try {
            filmsRepository.insert(
                name = name,
                image = image,
                price = price,
                category = category,
                rating = rating,
                year = year,
                director = director,
                description = description,
                orderAmount = orderAmount,
                userName = userName
            )
            // Başarılı ekleme sonrası loglama yapılabilir
            Log.d("FilmDetailViewModel", "Film başarıyla eklendi: $name")
        } catch (e: Exception) {
            // Hata durumlarını logla
            Log.e("FilmDetailViewModel", "Film ekleme başarısız: ${e.message}")
        }
    }
    }
}