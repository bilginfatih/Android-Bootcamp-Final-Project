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
class HomePageViewModel @Inject constructor(private var filmsRepository: FilmsRepository) : ViewModel() {
    // Ana film listesi
    var allFilmsList = MutableLiveData<List<FilmsData>>()

    // Kategorilere göre filtrelenmiş film listeleri
    var dramaFilmsList = MutableLiveData<List<FilmsData>>()
    var actionFilmsList = MutableLiveData<List<FilmsData>>()
    var sciFiFilmsList = MutableLiveData<List<FilmsData>>()
    var fantasticFilmsList = MutableLiveData<List<FilmsData>>()

    // Auto Slide filmler listesi
    var customFilmsList = MutableLiveData<List<FilmsData>>()

    init {
        // Uygulama başlarken filmleri çek
        filmsGet()
    }

    /**
     * Filmleri API'den çeker ve kategorilere göre filtreler.
     */
    private fun filmsGet() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // API'den tüm filmleri çek
                val films = filmsRepository.filmsGet()

                allFilmsList.value = films

                // Kategorilere göre filtrele
                dramaFilmsList.value = films.filter { it.category == "Drama" }
                actionFilmsList.value = films.filter { it.category == "Action" }
                sciFiFilmsList.value = films.filter { it.category == "Science Fiction" }
                fantasticFilmsList.value = films.filter { it.category == "Fantastic" }

                // Auto Slide list oluştur
                customFilmsList.value = listOfNotNull(
                    films.getOrNull(0),
                    films.getOrNull(1),
                    films.getOrNull(8),
                    films.getOrNull(12),
                    films.getOrNull(14),
                )

                // Başarıyla çekilen filmleri logla
                Log.d("HomePageViewModel", "Fetched films: $films")
            } catch (e: Exception) {
                // Hata durumunu logla
                Log.e("HomePageViewModel", "Error fetching films: ${e.message}", e)
            }
        }
    }
}