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
fun NavigateController(
    homePageViewModel: HomePageViewModel,
    filmDetailViewModel: FilmDetailViewModel,
    cartScreenViewModel: CartScreenViewModel,
    campaignViewModel: CampaignViewModel,
    orderViewModel: OrderViewModel
) {
    val navController = rememberNavController()

    // Navigation yapılandırması
    NavHost(navController = navController, startDestination = "homePage") {

        // Ana sayfa
        composable(
            "homePage?cartCount={cartCount}",
            arguments = listOf(navArgument("cartCount") {
                defaultValue = 0 // Varsayılan değer
                type = NavType.IntType // Veri tipi
            })
        ) { backStackEntry ->
            val cartCount = backStackEntry.arguments?.getInt("cartCount") ?: 0
            // Ana sayfa bileşeni
            HomePage(
                navController = navController,
                homePageViewModel = homePageViewModel,
                cartCount = cartCount,
                cartScreenViewModel = cartScreenViewModel
            )
        }

        // Film detay sayfası
        composable(
            "filmDetail/{film}",
            arguments = listOf(navArgument("film") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("film")
            val film = Gson().fromJson(json, FilmsData::class.java) // Film verilerini JSON'dan dönüştür
            FilmDetailScreen(
                film = film,
                filmDetailViewModel = filmDetailViewModel,
                navController = navController,
                cartScreenViewModel = cartScreenViewModel
            )
        }

        // Arama ekranı
        composable("searchScreen") {
            val allFilms = homePageViewModel.allFilmsList.observeAsState(listOf())
            SearchScreen(navController = navController, films = allFilms.value)
        }

        // Sepet ekranı
        composable(
            "cart?discount={discount}&campaignId={campaignId}",
            arguments = listOf(
                navArgument("discount") {
                    defaultValue = 0 // Varsayılan indirim
                    type = NavType.IntType
                },
                navArgument("campaignId") {
                    defaultValue = -1 // Varsayılan kampanya
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val discount = backStackEntry.arguments?.getInt("discount") ?: 0
            val campaignId = backStackEntry.arguments?.getInt("campaignId")
            val userName = "fatih_bilgin_test2" // Sabit kullanıcı adı
            CartScreen(
                navController = navController,
                userName = userName,
                cartScreenViewModel = cartScreenViewModel,
                filmDetailViewModel = filmDetailViewModel,
                discount = discount,
                selectedCampaignId = if (campaignId != -1) campaignId else null, // Geçerli kampanya ID
                orderViewModel = orderViewModel
            )
        }

        // Kampanya ekranı
        composable(
            "campaignScreen?campaignId={campaignId}",
            arguments = listOf(navArgument("campaignId") {
                type = NavType.IntType
                defaultValue = -1 // Varsayılan değer
            })
        ) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getInt("campaignId")
            CampaignScreen(
                navController = navController,
                initialCampaignId = campaignId, // Başlangıç kampanyası
                campaignViewModel = campaignViewModel
            )
        }

        // Siparişler ekranı
        composable("orders") {
            OrderScreen(
                orderViewModel = hiltViewModel(), // ViewModel Hilt ile sağlanır
                userName = "fatih_bilgin_test2",
                navController = navController
            )
        }
    }
}
