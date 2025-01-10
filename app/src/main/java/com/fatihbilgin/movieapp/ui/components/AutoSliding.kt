package com.fatihbilgin.movieapp.ui.components

import android.graphics.PorterDuff
import android.widget.RatingBar
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.data.entity.FilmsData
import com.fatihbilgin.movieapp.extensions.imageUrl
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSliding(pagerState: PagerState, filmsList: List<FilmsData>, navController: NavController) {

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(0.dp, 15.dp, 0.dp, 0.dp)
    ) { page ->
        MovieCard(film = filmsList[page], pagerState = pagerState, page = page, navController = navController)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCard(film: FilmsData, pagerState: PagerState, page: Int, navController: NavController) {
    val imageUrl = film.imageUrl()
    Card(
        modifier = Modifier
            .graphicsLayer {
                val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            }
            .fillMaxSize()
            .padding(15.dp, 0.dp, 15.dp, 0.dp)
            .clickable {
                val filmJson = Gson().toJson(film)
                navController.navigate("filmDetail/$filmJson")
            },
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            GlideImage(
                imageModel = imageUrl,
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(15.dp)
            ) {
                Text(
                    text = film.name,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                val ratingBar = RatingBar(
                    LocalContext.current, null, android.R.attr.ratingBarStyleSmall
                ).apply {
                    rating = film.rating.toFloat() / 2
                    progressDrawable.setColorFilter(
                        android.graphics.Color.parseColor("#FF0000"),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }
                AndroidView(
                    factory = { ratingBar },
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)
                )
                Text(
                    text = film.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)
                )
            }
        }
    }
}