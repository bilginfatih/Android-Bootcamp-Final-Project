package com.fatihbilgin.movieapp.ui.utils

import androidx.navigation.NavController

fun navigateToHomePage(navController: NavController, cartCount: Int) {
    navController.navigate("homePage?cartCount=$cartCount") {
        popUpTo("homePage") { inclusive = true }
        launchSingleTop = true
    }
}