package com.fatihbilgin.movieapp.ui.screen

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.ui.theme.DarkBlue
import com.fatihbilgin.movieapp.ui.viewmodel.CardScreenViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.HomePageViewModel

@Composable
fun HomePage(homePageViewModel: HomePageViewModel, navController: NavController, cartCount: Int, cardScreenViewModel: CardScreenViewModel) {
    Scaffold() { paddingValues ->
        // Movie content
        MovieContent(
            paddingValues = paddingValues,
            homePageViewModel = homePageViewModel,
            navController = navController,
            cartCount = cartCount,
            cardScreenViewModel = cardScreenViewModel
        )
    }
}
