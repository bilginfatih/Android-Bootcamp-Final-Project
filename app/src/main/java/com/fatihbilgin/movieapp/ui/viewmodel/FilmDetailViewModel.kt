package com.fatihbilgin.movieapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.data.repo.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmDetailViewModel @Inject constructor(private val filmsRepository: FilmsRepository) : ViewModel() {
    var _cartItemCount = MutableLiveData(0)
    val cartItemCount: LiveData<Int> get() = _cartItemCount

    fun incrementCartItemCount() {
        _cartItemCount.value = (_cartItemCount.value ?: 0) + 1
    }

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
            filmsRepository.insert(name, image, price, category, rating, year, director, description, orderAmount, userName)

        }
    }
}
