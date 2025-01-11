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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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

@Composable
fun SearchScreen(navController: NavController, films: List<FilmsData>) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredFilms = films.filter { film ->
        // Arama sorgusuna göre film ismini veya kategorisini filtreleme
        film.name.contains(searchQuery, ignoreCase = true) ||
                film.category.contains(searchQuery, ignoreCase = true)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackGroundColor) // Arka plan rengi
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it }, // Kullanıcının arama sorgusunu güncelleme
                label = { Text("Filmleri Ara", color = Color.White) },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Clear",
                        tint = Color.White,
                        modifier = Modifier.clickable { navController.popBackStack() } // Geri butonu
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.searchmid),
                        contentDescription = "Search",
                        tint = Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)
                    .clip(RoundedCornerShape(16.dp)), // Köşeleri yuvarlama
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
                verticalArrangement = Arrangement.spacedBy(8.dp) // Elemanlar arasındaki boşluk
            ) {
                items(filteredFilms) { film ->
                    FilmCardWithDetails(film = film, navController = navController) // Film kartını listeleme
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
                // Film detay sayfasına yönlendirme
                val filmJson = Gson().toJson(film)
                navController.navigate("filmDetail/$filmJson")
            },
        shape = RoundedCornerShape(12.dp), // Kartın köşelerini yuvarlama
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Kart gölgesi
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBlue) // Kartın arka plan rengi
            ) {
                val imageWidth = maxWidth * 0.4f // Görsel genişliği
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Film görseli
                    GlideImage(
                        imageModel = film.imageUrl(),
                        contentDescription = film.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(imageWidth) // Genişlik, kart genişliğinin %40'ı
                            .fillMaxHeight()   // Kartın tüm yüksekliği
                            .clip(RoundedCornerShape(8.dp)) // Köşeleri yuvarlama
                    )

                    // Film detayları
                    Column(modifier = Modifier.fillMaxSize().background(DarkBlue)) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp, bottom = 34.dp, top = 12.dp)
                        ) {
                            Text(
                                text = film.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.TopCenter) // Üst merkeze hizalanır
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
                                    // Rating butonu
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
                                    // Yıl bilgisi butonu
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
                            // Film açıklaması
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
        }
    }
}


