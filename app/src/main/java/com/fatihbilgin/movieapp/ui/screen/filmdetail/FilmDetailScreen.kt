package com.fatihbilgin.movieapp.ui.screen.filmdetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.extensions.imageUrl
import com.fatihbilgin.movieapp.ui.components.BottomActionBar
import com.fatihbilgin.movieapp.ui.components.CommonTopAppBar
import com.fatihbilgin.movieapp.ui.theme.BackGroundColor
import com.fatihbilgin.movieapp.ui.viewmodel.CartScreenViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.FilmDetailViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.fatihbilgin.movieapp.ui.utils.navigateToHomePage

@Composable
fun FilmDetailScreen(
    film: FilmsData?,
    filmDetailViewModel: FilmDetailViewModel,
    navController: NavController,
    cartScreenViewModel: CartScreenViewModel
) {
    if (film != null) {
        val cartItemCount = filmDetailViewModel._cartItemCount.observeAsState(0)

        // Geri tuşu için özel handler
        BackHandler {
            navigateToHomePage(navController, cartItemCount.value)
        }

        Scaffold(
            topBar = {
                CommonTopAppBar(
                    title = "",
                    navController = navController,
                    onBackClick = { navigateToHomePage(navController, cartItemCount.value) },
                    backgroundColor = Color.Transparent,
                    isTitleCentered = false,

                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackGroundColor)
            ) {
                // Film görseli
                GlideImage(
                    imageModel = film.imageUrl(),
                    contentDescription = film.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenHeightDp.dp / 2 + 100.dp)
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                        .align(Alignment.TopCenter)
                )

                // Film detaylarının olduğu içerik
                FilmDetailContent(
                    film = film,
                    paddingValues = paddingValues
                )

                // Sepete ekleme butonu
                AddToCartButton(
                    film = film,
                    filmDetailViewModel = filmDetailViewModel,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 165.dp)
                )

                // Bottom Action Bar
                BottomActionBar(
                    cartItemCount = cartItemCount.value,
                    navController = navController,
                    cartScreenViewModel = cartScreenViewModel
                )
            }
        }
    } else {
        // Film detayları yüklenirken gösterilecek loading ekranı
        LoadingIndicator()
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGroundColor),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}
