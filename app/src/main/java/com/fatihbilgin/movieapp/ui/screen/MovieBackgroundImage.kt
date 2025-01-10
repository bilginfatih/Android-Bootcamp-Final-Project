package com.fatihbilgin.movieapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fatihbilgin.movieapp.R
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.extensions.imageUrl
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieBackgroundImage(currentPage: Int, filmsList: List<FilmsData>) {
    if (currentPage in filmsList.indices) {
        val imageUrl = filmsList[currentPage].imageUrl()
        GlideImage(
            imageModel = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .graphicsLayer {
                    alpha = 1f
                }
                .blur(20.dp)
                .clip(RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp))

        )
    }
}