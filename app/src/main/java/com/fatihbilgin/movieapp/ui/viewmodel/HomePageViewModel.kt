package com.fatihbilgin.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.data.repo.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(var filmsRepository: FilmsRepository) : ViewModel() {
    // Ana film listesi
    var allFilmsList = MutableLiveData<List<FilmsData>>()

    // Kategorilere göre filtrelenmiş film listeleri
    var dramaFilmsList = MutableLiveData<List<FilmsData>>()
    var actionFilmsList = MutableLiveData<List<FilmsData>>()
    var sciFiFilmsList = MutableLiveData<List<FilmsData>>()
    var fantasticFilmsList = MutableLiveData<List<FilmsData>>()

    // Custom film listesi (belirtilen indeksler)
    var customFilmsList = MutableLiveData<List<FilmsData>>()


    // Başlangıçta çağrılacak ve film verisini çekecek fonksiyon
    init {
        filmsGet()
    }

    // Filmleri çekip kategorilere göre filtrele ve custom list'yi oluştur
    private fun filmsGet() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val films = filmsRepository.filmsGet()
                allFilmsList.value = films

                // Kategorilere göre filtrele
                dramaFilmsList.value = films.filter { it.category == "Drama" }
                actionFilmsList.value = films.filter { it.category == "Action" }
                sciFiFilmsList.value = films.filter { it.category == "Science Fiction" }
                fantasticFilmsList.value = films.filter { it.category == "Fantastic" }

                // Custom list oluştur
                customFilmsList.value = listOf(
                    films.getOrNull(0), // 0. indeks
                    films.getOrNull(1), // 1. indeks
                    films.getOrNull(8), // 8. indeks
                    films.getOrNull(12), // 12. indeks
                    films.getOrNull(14)  // 14. indeks
                ).filterNotNull() // Null olmayan elemanları al

                // Listeyi logla
                Log.d("LogFatih2: FilmsList", "Fetched films: ${films}")
            } catch (e: Exception) {
                Log.e("LogFatih: FilmsList", "Error fetching films: ${e.message}")
            }
        }
    }
}
