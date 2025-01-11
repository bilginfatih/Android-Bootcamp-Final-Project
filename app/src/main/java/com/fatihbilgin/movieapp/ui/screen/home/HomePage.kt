package com.fatihbilgin.movieapp.ui.screen.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.ui.viewmodel.CartScreenViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.HomePageViewModel

@Composable
fun HomePage(homePageViewModel: HomePageViewModel, navController: NavController, cartCount: Int, cartScreenViewModel: CartScreenViewModel) {
    Scaffold() { paddingValues ->
        // Movie content
        MovieContent(
            paddingValues = paddingValues,
            homePageViewModel = homePageViewModel,
            navController = navController,
            cartCount = cartCount,
            cartScreenViewModel = cartScreenViewModel
        )
    }
}