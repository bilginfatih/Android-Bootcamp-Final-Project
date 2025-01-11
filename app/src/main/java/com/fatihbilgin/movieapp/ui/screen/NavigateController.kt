package com.fatihbilgin.movieapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.ui.screen.filmdetail.FilmDetailScreen
import com.fatihbilgin.movieapp.ui.screen.home.HomePage
import com.fatihbilgin.movieapp.ui.screen.order.OrderScreen
import com.fatihbilgin.movieapp.ui.viewmodel.CampaignViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.CartScreenViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.FilmDetailViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.HomePageViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.OrderViewModel
import com.google.gson.Gson

@Composable
fun NavigateController(homePageViewModel : HomePageViewModel,
                       filmDetailViewModel: FilmDetailViewModel,
                       cartScreenViewModel: CartScreenViewModel,
                       campaignViewModel: CampaignViewModel,
                       orderViewModel: OrderViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "homePage") {
        composable(
            "homePage?cartCount={cartCount}",
            arguments = listOf(navArgument("cartCount") { defaultValue = 0; type = NavType.IntType })
        ) { backStackEntry ->
            val cartCount = backStackEntry.arguments?.getInt("cartCount") ?: 0
            HomePage(navController = navController, homePageViewModel = homePageViewModel, cartCount = cartCount, cartScreenViewModel = cartScreenViewModel)
        }
        // Film detay sayfası
        composable(
            "filmDetail/{film}",
            arguments = listOf(navArgument("film") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("film")
            val film = Gson().fromJson(json, FilmsData::class.java)
            FilmDetailScreen(film = film, filmDetailViewModel = filmDetailViewModel, navController = navController, cartScreenViewModel = cartScreenViewModel)
        }
        composable("searchScreen") {
            val allFilms = homePageViewModel.allFilmsList.observeAsState(listOf())
            SearchScreen(navController = navController, films = allFilms.value)
        }
        composable(
            "cart?discount={discount}&campaignId={campaignId}",
            arguments = listOf(navArgument("discount") { defaultValue = 0; type = NavType.IntType },
                navArgument("campaignId") { defaultValue = -1; type = NavType.IntType })
        ) { backStackEntry ->
            val discount = backStackEntry.arguments?.getInt("discount") ?: 0
            val campaignId = backStackEntry.arguments?.getInt("campaignId")
            val userName = "fatih_bilgin_test2"
            CartScreen(
                navController = navController,
                userName = userName,
                cartScreenViewModel = cartScreenViewModel,
                filmDetailViewModel = filmDetailViewModel,
                discount = discount,
                selectedCampaignId = if (campaignId != -1) campaignId else null,
                orderViewModel = orderViewModel
            )
        }

        composable(
            "campaignScreen?campaignId={campaignId}",
            arguments = listOf(navArgument("campaignId") { type = NavType.IntType; defaultValue = -1 })
        ) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getInt("campaignId")
            CampaignScreen(navController = navController, initialCampaignId = campaignId, campaignViewModel = campaignViewModel)
        }

        composable("orders") {
            OrderScreen(
                orderViewModel = hiltViewModel(),
                userName = "fatih_bilgin_test2",
                navController = navController
            )
        }

    }

    }