package com.fatihbilgin.movieapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.ui.components.AutoSliding
import com.fatihbilgin.movieapp.ui.components.BottomActionBar
import com.fatihbilgin.movieapp.ui.components.FilmCard
import com.fatihbilgin.movieapp.ui.theme.BackGroundColor
import com.fatihbilgin.movieapp.ui.viewmodel.CardScreenViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.HomePageViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieContent(
    paddingValues: PaddingValues,
    homePageViewModel: HomePageViewModel,
    navController: NavController,
    cartCount: Int,
    cardScreenViewModel: CardScreenViewModel
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
        BottomActionBar(cartItemCount = cartCount, navController = navController, cardScreenViewModel = cardScreenViewModel)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieHeaderSection(pagerState: PagerState, filmsList: List<FilmsData>, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        MovieBackgroundImage(
            currentPage = pagerState.currentPage,
            filmsList = filmsList
        )
        AutoSliding(
            pagerState = pagerState,
            filmsList = filmsList,
            navController = navController
        )
    }
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
                FilmCard(film,navController)
            }
        }
    }
}
