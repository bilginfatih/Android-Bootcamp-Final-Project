package com.fatihbilgin.movieapp.ui.screen

import android.graphics.PorterDuff
import android.widget.RatingBar
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fatihbilgin.movieapp.R
import com.fatihbilgin.movieapp.data.entity.MovieCartData
import com.fatihbilgin.movieapp.extensions.imageUrl
import com.fatihbilgin.movieapp.ui.theme.BackGroundColor
import com.fatihbilgin.movieapp.ui.theme.DarkBlue
import com.fatihbilgin.movieapp.ui.theme.Red
import com.fatihbilgin.movieapp.ui.viewmodel.CardScreenViewModel
import com.fatihbilgin.movieapp.ui.viewmodel.FilmDetailViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    userName: String,
    cardScreenViewModel: CardScreenViewModel,
    filmDetailViewModel: FilmDetailViewModel,
    discount: Int,
    selectedCampaignId: Int?
) {
    // Sepet filmlerini gözlemle
    val cartFilms = cardScreenViewModel.cartFilms.observeAsState(listOf())

    // Tüm filmlerin toplam fiyatını hesaplayın
    val totalPrice = cartFilms.value.sumOf { it.price * it.orderAmount }

    // Animasyonlu toplam fiyat
    val animatedTotalPrice by animateIntAsState(targetValue = totalPrice)

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_cart))
    val isPlaying by remember { mutableStateOf(true) }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever
    )

    // ViewModel'den sepet filmlerini al
    LaunchedEffect(Unit) {
        cardScreenViewModel.fetchAndMergeCartItems(userName)
    }

    BackHandler {
        navController.popBackStack()
        filmDetailViewModel._cartItemCount = MutableLiveData(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {  Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sepetim",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                 },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {

                    }) {

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackGroundColor),
            )
        }
    ) { paddingValues ->
        if (cartFilms.value.isEmpty()) {
            // Boş sepet durumu
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = BackGroundColor),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = composition,
                    modifier = Modifier.size(250.dp),
                    progress = { progress }
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(BackGroundColor)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(cartFilms.value) { film ->
                        CartItem(
                            film = film,
                            orderAmount = film.orderAmount,
                            onDecrement = { cartId -> cardScreenViewModel.deleteMovieByDecrement(cartId, userName) },
                            filmDetailViewModel = filmDetailViewModel,
                            cardScreenViewModel = cardScreenViewModel
                        )
                    }
                }
            }
            BottomOrderButton(
                animatedTotalPrice = animatedTotalPrice,
                navController = navController,
                discount = discount,
                selectedCampaignId = selectedCampaignId
            )
        }
    }
}

@Composable
fun BottomOrderButton(animatedTotalPrice: Int, navController: NavController, discount: Int, selectedCampaignId: Int?) {
    //Kampanya hesaplama
    val animatedTotalDiscountPrice: Double =
        animatedTotalPrice.toDouble() - ((animatedTotalPrice.toDouble() * discount.toDouble()) / 100.0)

    val formatter = DecimalFormat("#,##0.00")
    val formattedPrice = formatter.format(animatedTotalDiscountPrice).replace(".", ",") // Noktayı virgüle çevir



    //Lottie Animasyon
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.giftlottie))
    val isPlaying by remember { mutableStateOf(true) }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever
    )
    // Animasyonlu y-offset (dp olarak başlangıç ve bitiş pozisyonu)
    val offsetY = remember { Animatable(200f) } // Başlangıç y-offset (ekranın altından başlar)

    // Animasyon başlatılır
    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = 0f, // Nihai pozisyon (normal yerine oturur)
            animationSpec = tween(
                durationMillis = 500, // Animasyon süresi
                easing = LinearOutSlowInEasing // Yavaşça duran bir eğri
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .offset(y = offsetY.value.dp) // Animasyonu uygulamak için offset
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.1f) // Sabit yükseklik
                .padding(bottom = 18.dp)
                .background(Red, shape = RoundedCornerShape(8.dp))
                .clickable(enabled = true) { /* Ödeme Tamamla Aksiyonu */ }
        ) {
            // Sol üst köşeye yuvarlak IconButton ekleniyor
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                IconButton(
                    onClick = { navController.navigate("campaignScreen?campaignId=${selectedCampaignId ?: -1}") },
                    modifier = Modifier
                        .offset(x = (-12).dp, y = (-25).dp) // Daha yukarı ve sola yakın pozisyon
                        .size(48.dp) // Butonun boyutu
                        .background(DarkBlue, shape = CircleShape) // Arka plan ve yuvarlak şekil
                ) {
                    when (discount) {
                        5 -> {
                            Image(
                                painter = painterResource(id = R.drawable.off5),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                        15 -> {
                            Image(
                                painter = painterResource(id = R.drawable.off15),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                        25 -> {
                            Image(
                                painter = painterResource(id = R.drawable.off25),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                        else -> {
                            LottieAnimation(
                                composition = composition,
                                progress = { progress }
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 28.dp, end = 14.dp), // Yatay padding
                horizontalArrangement = Arrangement.SpaceBetween, // Sağ ve sol hizalama
                verticalAlignment = Alignment.CenterVertically // Dikey ortalama
            ) {
                Text(
                    text = "Ödemeyi Tamamla",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f)
                        .padding(top = 6.dp, bottom = 6.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (selectedCampaignId != null) {
                        Text(
                            text = "$${animatedTotalPrice},00",
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.LineThrough
                            ),
                            fontWeight = FontWeight.Bold,
                            color = Color.LightGray
                        )
                        Text(
                            text = "$${formattedPrice}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "$${animatedTotalPrice},00",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                }
            }
        }
    }
}



@Composable
fun CartItem(
    film: MovieCartData,
    orderAmount: Int, onDecrement: (Int) -> Unit,
    filmDetailViewModel: FilmDetailViewModel,
    cardScreenViewModel: CardScreenViewModel
    ) {
    // Animasyonlu state'ler
    val animatedOrderAmount by animateIntAsState(targetValue = orderAmount)
    val animatedPrice by animateIntAsState(targetValue = orderAmount * film.price)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
            .background(BackGroundColor),
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
                val imageWidth = maxWidth * 0.25f // Card genişliğinin %40'ı
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
                                .padding(start = 12.dp, end = 12.dp, bottom = 8.dp, top = 8.dp)
                        ) {
                            Text(
                                text = film.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.TopStart)
                            )
                            Text(
                                text = "$${film.price}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.LightGray,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 35.dp)

                            )
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
                            AndroidView(
                                factory = { ratingBar },
                                modifier = Modifier.align(Alignment.TopEnd).padding(end = 34.dp, top = 4.dp)
                            )
                            Text(
                                text = "${film.rating}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.TopEnd)
                            )
                            Text(
                                text = "$${animatedPrice}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )


                            Box(modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(start = 60.dp)
                            ) {
                                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End) {
                                    IconButton(
                                        onClick = {
                                            onDecrement(film.cartId)
                                        },
                                        modifier = Modifier
                                            .background(Red, shape = RoundedCornerShape(8.dp))
                                            .align(Alignment.Bottom)
                                            .height(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (orderAmount > 1) Icons.Default.Clear else Icons.Default.Delete,
                                            contentDescription = "-", tint = Color.White)
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = animatedOrderAmount.toString(),
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.Bottom)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    IconButton(
                                        onClick = {
                                            filmDetailViewModel.insert(film.name, film.image, film.price, film.category, film.rating, film.year, film.director, film.description, 1, "fatih_bilgin_test2")
                                            cardScreenViewModel.fetchAndMergeCartItems(userName = "fatih_bilgin_test2")
                                        },
                                        modifier = Modifier
                                            .background(Red, shape = RoundedCornerShape(8.dp))
                                            .align(Alignment.Bottom)
                                            .height(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "+", tint = Color.White)
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}


