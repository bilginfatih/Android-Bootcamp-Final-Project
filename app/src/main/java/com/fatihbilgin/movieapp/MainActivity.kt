package com.fatihbilgin.movieapp

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fatihbilgin.movieapp.ui.screen.HomePage
import com.fatihbilgin.movieapp.ui.screen.NavigateController
import com.fatihbilgin.movieapp.ui.screen.OnboardingScreen


import com.fatihbilgin.movieapp.ui.theme.MovieAppTheme
import com.fatihbilgin.movieapp.ui.viewmodel.CardScreenViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.FilmDetailViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val homePageViewModel : HomePageViewModel by viewModels()
    val filmDetailViewModel : FilmDetailViewModel by viewModels()
    val cardScreenViewModel : CardScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        enableEdgeToEdge()
        // SharedPreferences ile onboarding kontrolü
        val sharedPreferences = getSharedPreferences("movieAppPrefs", MODE_PRIVATE)
        val isOnboardingComplete = sharedPreferences.getBoolean("ONBOARDING_COMPLETE", false)
        setContent {
            MovieAppTheme {
                if (isOnboardingComplete) {
                    // Eğer onboarding tamamlandıysa, mevcut navigasyon yapısını kullan

                    NavigateController(
                        homePageViewModel = homePageViewModel,
                        filmDetailViewModel = filmDetailViewModel,
                        cardScreenViewModel = cardScreenViewModel
                    )
                } else {
                    // Onboarding ekranı göster
                    OnboardingScreen(
                        sharedPreferences = sharedPreferences,
                        onGetStartedClicked = {
                            // Onboarding tamamlandı olarak kaydet ve ana ekrana geç
                            sharedPreferences.edit().putBoolean("ONBOARDING_COMPLETE", true).apply()
                            recreate() // MainActivity'yi yeniden başlat
                        }
                    )
                }
            }
        }
    }
}
