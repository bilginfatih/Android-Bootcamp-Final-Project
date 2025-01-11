package com.fatihbilgin.movieapp.ui.screen.filmdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fatihbilgin.movieapp.R
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.ui.theme.DarkBlue
import com.fatihbilgin.movieapp.ui.theme.Red
import com.fatihbilgin.movieapp.ui.viewmodel.FilmDetailViewModel

@Composable
fun FilmDetailContent(
    film: FilmsData,
    paddingValues: PaddingValues,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f)
                .padding(paddingValues)
                .background(Color.Black.copy(alpha = 0.7f), shape = RoundedCornerShape(32.dp))
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            ) {
                // Film adı ve fiyatı
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

                // Film kategorisi
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

                // Yönetmen ve Yıl
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

                // Film açıklaması
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
    }

}

@Composable
fun AddToCartButton(
    film: FilmsData,
    filmDetailViewModel: FilmDetailViewModel,
    modifier: Modifier
) {
    TextButton(
        onClick = {
            filmDetailViewModel.incrementCartItemCount()
            filmDetailViewModel.insert(
                film.name, film.image, film.price, film.category,
                film.rating, film.year, film.director, film.description, 1, "fatih_bilgin_test2"
            )
        },
        modifier = modifier
            .width(175.dp)
            .background(Red, shape = RoundedCornerShape(24.dp))
    ) {
        Text(
            text = "Sepete Ekel",
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
}
