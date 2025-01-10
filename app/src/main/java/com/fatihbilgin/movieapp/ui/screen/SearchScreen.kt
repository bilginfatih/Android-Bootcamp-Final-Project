package com.fatihbilgin.movieapp.ui.screen

import android.graphics.PorterDuff
import android.widget.RatingBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.R
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.extensions.imageUrl
import com.fatihbilgin.movieapp.ui.theme.BackGroundColor
import com.fatihbilgin.movieapp.ui.theme.DarkBlue
import com.fatihbilgin.movieapp.ui.theme.Red
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, films: List<FilmsData>) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredFilms = films.filter { film ->
        film.name.contains(searchQuery, ignoreCase = true) ||
                film.category.contains(searchQuery, ignoreCase = true)
    }

    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackGroundColor)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Movies", color = Color.White) },
                trailingIcon = {
                    Icon(painter = painterResource(id = R.drawable.back),
                    contentDescription = "Clear", tint = Color.White,
                    modifier = Modifier.clickable { navController.popBackStack() })
                               },
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.searchmid), contentDescription = "Search", tint = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = DarkBlue,
                    focusedContainerColor = DarkBlue,
                    unfocusedIndicatorColor = DarkBlue,
                    focusedIndicatorColor = DarkBlue,
                    focusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                ),

            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(1.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredFilms) { film ->
                    FilmCardWithDetails(film = film, navController = navController)
                }
            }
        }
    }
}


@Composable
fun FilmCardWithDetails(film: FilmsData, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(225.dp)
            .background(BackGroundColor)
            .clickable {
                val filmJson = Gson().toJson(film)
                navController.navigate("filmDetail/$filmJson")
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBlue)
            ) {
                val imageWidth = maxWidth * 0.4f // Card genişliğinin %40'ı
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Görsel (%40'lık alan)
                    GlideImage(
                        imageModel = film.imageUrl(),
                        contentDescription = film.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(imageWidth) // Genişlik, card genişliğinin %40'ı
                            .fillMaxHeight()   // Card'ın tüm yüksekliği
                            .clip(RoundedCornerShape(8.dp))
                    )

                    // Geri kalan %60'lık alan
                    Column(modifier = Modifier.fillMaxSize().background(DarkBlue)) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth() // Geriye kalan genişliği kaplar
                                .padding(start = 24.dp, end = 24.dp, bottom = 34.dp, top = 12.dp)
                        ) {
                            Text(
                                text = film.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.TopCenter) // Geri kalan alanın üst merkezine hizalanır
                            )
                            Row(modifier = Modifier.fillMaxSize()) {
                                Box(modifier = Modifier.fillMaxSize().padding(bottom = 24.dp)) {
                                    val ratingBar = RatingBar(
                                        LocalContext.current, null, android.R.attr.ratingBarStyleSmall
                                    ).apply {
                                        numStars = 1
                                        rating = 1.0.toFloat()
                                        progressDrawable.setColorFilter(
                                            android.graphics.Color.parseColor("#FFD700"),
                                            PorterDuff.Mode.SRC_ATOP
                                        )
                                    }
                                    TextButton(
                                        onClick = {},
                                        modifier = Modifier
                                            .background(Red, shape = RoundedCornerShape(12.dp))
                                            .align(Alignment.CenterStart)
                                            .height(36.dp)
                                    ) {
                                        AndroidView(
                                            factory = { ratingBar },
                                            modifier = Modifier.padding(end = 6.dp)
                                        )
                                        Text(
                                            text = film.rating.toString(),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    TextButton(
                                        onClick = {},
                                        modifier = Modifier
                                            .background(Red, shape = RoundedCornerShape(12.dp))
                                            .align(Alignment.CenterEnd)
                                            .height(36.dp)
                                    ) {
                                        Text(
                                            text = film.year.toString(),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                            Text(
                                text = film.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
            // Film details
            Column(
                modifier = Modifier.fillMaxHeight().background(DarkBlue),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = film.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

