package com.fatihbilgin.movieapp.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatihbilgin.movieapp.data.entity.MovieCartData
import com.fatihbilgin.movieapp.data.repo.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(private val filmsRepository: FilmsRepository) : ViewModel() {
    var _cartFilms = MutableLiveData<List<MovieCartData>>()
    val cartFilms: LiveData<List<MovieCartData>> = _cartFilms

    // Toplam order amount LiveData
    private val _totalOrderAmount = MutableLiveData<Int>()
    val totalOrderAmount: LiveData<Int> = _totalOrderAmount

    init {
        fetchAndMergeCartItems("fatih_bilgin_test2")
    }

    // API'den verileri çek ve aynı isimdeki filmleri birleştir
    fun fetchAndMergeCartItems(userName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                delay(300L)
                val fetchedCartItems = filmsRepository.movieCartGet(userName)
                val mergedCartItems = fetchedCartItems
                    .groupBy { it.name }
                    .map { (name, items) ->
                        val totalOrderAmount = items.sumOf { it.orderAmount }
                        val firstItem = items.first()
                        firstItem.copy(orderAmount = totalOrderAmount)
                    }
                _cartFilms.value = mergedCartItems

                // Toplam order amount hesapla
                _totalOrderAmount.value = mergedCartItems.sumOf { it.orderAmount }
            } catch (e: Exception) {
                // Hata işleme
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun deleteMovieByDecrement(cartId: Int, userName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val currentCartItems = _cartFilms.value ?: return@launch

                    filmsRepository.deleteMovie(cartId, userName)
                    // Order amount'u bir azalt
                    val updatedCartItems = currentCartItems.map {
                        if (it.cartId == cartId && it.orderAmount > 0) {
                            val remainingAmount = it.orderAmount - 1
                            it.copy(orderAmount = remainingAmount.coerceAtLeast(0))
                        } else it
                    }.filterNot { it.orderAmount == 0 } // Sıfır olanları kaldır

                    _cartFilms.value = updatedCartItems
                    _totalOrderAmount.value = updatedCartItems.sumOf { it.orderAmount }
            fetchAndMergeCartItems(userName)


        }
    }

}



