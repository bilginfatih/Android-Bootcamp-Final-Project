package com.fatihbilgin.movieapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.skydoves.landscapist.glide.GlideImage
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.extensions.imageUrl
import com.google.gson.Gson

@Composable
fun FilmCard(film: FilmsData, navController: NavController) {
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

