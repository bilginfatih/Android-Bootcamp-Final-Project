package com.fatihbilgin.movieapp.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.R
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.extensions.imageUrl
import com.fatihbilgin.movieapp.ui.components.BottomActionBar
import com.fatihbilgin.movieapp.ui.theme.BackGroundColor
import com.fatihbilgin.movieapp.ui.theme.DarkBlue
import com.fatihbilgin.movieapp.ui.theme.Red
import com.fatihbilgin.movieapp.ui.viewmodel.CardScreenViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.FilmDetailViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun FilmDetailScreen(film: FilmsData?,
                     filmDetailViewModel: FilmDetailViewModel,
                     navController: NavController,
                     cardScreenViewModel: CardScreenViewModel) {
    if (film != null) {
        val cartItemCount = filmDetailViewModel.cartItemCount.observeAsState(0)
        //val orderAmountCount = filmDetailViewModel.orderAmountCount.observeAsState(1)
        BackHandler {
            navigateToHomePage(navController, cartItemCount.value)
            //filmDetailViewModel._orderAmountCount = MutableLiveData(1)
        }
        Scaffold(
            topBar = { FilmDetailTopAppBar(navController, cartItemCount.value, filmDetailViewModel) }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().background(BackGroundColor)) {
                // Film image at the top
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

                // Film Details
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.8f)
                        .padding(paddingValues)
                        .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(32.dp))
                ) {
                    // Inner container with text and buttons
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    ) {
                        // Film details (name, price, etc.)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = film.name,
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${film.price}$",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Other details (category, rating, description, etc.)
                        TextButton(
                            onClick = {},
                            modifier = Modifier
                                .background(DarkBlue, shape = RoundedCornerShape(24.dp))
                                .align(Alignment.CenterHorizontally)
                                .height(36.dp)
                        ) {
                            Text(
                                text = film.category,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Director and Rating
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${film.director} - ${film.year}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                            Text(
                                text = "IMDB: ${film.rating}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Film Description
                        Text(
                            text = "Summary",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = film.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
                // Add to Cart Button
                TextButton(
                    onClick = {
                        filmDetailViewModel.incrementCartItemCount()
                        filmDetailViewModel.insert(film.name, film.image, film.price, film.category, film.rating, film.year, film.director, film.description, 1, "fatih_bilgin_test2")
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 165.dp)
                        .width(175.dp)
                        .background(Red, shape = RoundedCornerShape(24.dp))
                ) {
                    Text(
                        text = "Add to Cart",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.btn_3),
                        contentDescription = "Add to Cart",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }

                // Bottom Action Bar
                BottomActionBar(cartItemCount =  cartItemCount.value, navController = navController, cardScreenViewModel = cardScreenViewModel )
            }
        }
    } else {
        LoadingIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailTopAppBar(navController: NavController, cartItemCount: Int, filmDetailViewModel: FilmDetailViewModel) {
    TopAppBar(
        title = { Text(text = "") },
        navigationIcon = {
            IconButton(onClick = {
                navigateToHomePage(navController, cartItemCount)
                //filmDetailViewModel._orderAmountCount = MutableLiveData(1)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier.zIndex(1f)
    )
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

fun navigateToHomePage(navController: NavController, cartCount: Int) {
    navController.navigate("homePage?cartCount=$cartCount") {
        popUpTo("homePage") { inclusive = true }
        launchSingleTop = true
    }
}











