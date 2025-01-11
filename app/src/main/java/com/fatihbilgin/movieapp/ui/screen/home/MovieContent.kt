package com.fatihbilgin.movieapp.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.extensions.imageUrl
import com.fatihbilgin.movieapp.ui.components.BottomActionBar
import com.fatihbilgin.movieapp.ui.theme.BackGroundColor
import com.fatihbilgin.movieapp.ui.viewmodel.CartScreenViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.HomePageViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieContent(
    paddingValues: PaddingValues,
    homePageViewModel: HomePageViewModel,
    navController: NavController,
    cartCount: Int,
    cartScreenViewModel: CartScreenViewModel
) {

    val filmsList = homePageViewModel.customFilmsList.observeAsState(listOf())
    val dramaFilms = homePageViewModel.dramaFilmsList.observeAsState(listOf())
    val actionFilms = homePageViewModel.actionFilmsList.observeAsState(listOf())
    val sciFiFilms = homePageViewModel.sciFiFilmsList.observeAsState(listOf())
    val fantasticFilms = homePageViewModel.fantasticFilmsList.observeAsState(listOf())

    val pagerState = rememberPagerState(
        pageCount = { filmsList.value.size },
        initialPage = 0
    )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackGroundColor)
                .padding(paddingValues)
        ) {
            item {
                MovieHeaderSection(pagerState, filmsList.value, navController)
            }
            item {
                CategorySection("Drama", dramaFilms.value, navController)
            }
            item {
                CategorySection("Action", actionFilms.value,navController)
            }
            item {
                CategorySection("Science Fiction", sciFiFilms.value,navController)
            }
            item {
                CategorySection("Fantastic", fantasticFilms.value,navController)
            }
        }
        BottomActionBar(cartItemCount = cartCount, navController = navController, cartScreenViewModel = cartScreenViewModel)
}

@Composable
fun CategorySection(categoryName: String, films: List<FilmsData>, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(films) { film ->
                CategoryCard(film,navController)
            }
        }
    }
}

@Composable
fun CategoryCard(film: FilmsData, navController: NavController) {
    Card(
        modifier = Modifier
            .size(150.dp, 220.dp)
            .padding(2.dp)
            .clickable {
                val filmJson = Gson().toJson(film)
                navController.navigate("filmDetail/$filmJson")
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        ) {
            GlideImage(
                imageModel = film.imageUrl(),
                contentDescription = film.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Film Name (Bottom-Start)
            Text(
                text = film.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(4.dp)
            )
            // Film Price (Bottom-End)
            Text(
                text = "${film.price}$", // Film fiyatını buraya ekliyoruz
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(4.dp)
            )
        }
    }
}
