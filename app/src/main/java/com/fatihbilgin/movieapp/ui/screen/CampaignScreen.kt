package com.fatihbilgin.movieapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.R
import com.fatihbilgin.movieapp.data.entity.CampaignData
import com.fatihbilgin.movieapp.ui.components.CommonTopAppBar
import com.fatihbilgin.movieapp.ui.theme.BackGroundColor
import com.fatihbilgin.movieapp.ui.theme.DarkBlue
import com.fatihbilgin.movieapp.ui.theme.Red
import com.fatihbilgin.movieapp.ui.viewmodel.CampaignViewModel

@Composable
fun CampaignScreen(
    navController: NavController,
    initialCampaignId: Int?,
    campaignViewModel: CampaignViewModel
) {
    // Seçilen kampanyayı başlangıçta ayarlamak için
    LaunchedEffect(initialCampaignId) {
        if (initialCampaignId != null && initialCampaignId != -1) {
            campaignViewModel.selectCampaign(initialCampaignId)
        }
    }

    // Kampanya listesini statik olarak tanımlıyoruz
    val campaigns = listOf(
        CampaignData(
            id = 1,
            title = "%5 indirim",
            details = "%5 indirim sizi bekliyor",
            imageRes = R.drawable.off5,
            discount = 5
        ),
        CampaignData(
            id = 2,
            title = "%15 indirim",
            details = "%15 indirim sizi bekliyor",
            imageRes = R.drawable.off15,
            discount = 15
        ),
        CampaignData(
            id = 3,
            title = "%25 indirim",
            details = "%25 indirim sizi bekliyor",
            imageRes = R.drawable.off25,
            discount = 25
        )
    )

    // Seçili kampanyayı gözlemlemek için
    val selectedCampaignId by campaignViewModel.selectedCampaignId.observeAsState()

    Scaffold(
        topBar = {
            // Ortak TopAppBar kullanımı
            CommonTopAppBar(
                title = "Kampanyalar",
                navController = navController,
                actions = { IconButton(onClick = {}) { } }
            )
        }
    ) { paddingValues ->
        // Kampanya kartlarını listelemek için LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackGroundColor),
            verticalArrangement = Arrangement.spacedBy(8.dp), // Kartlar arasındaki boşluk
            contentPadding = PaddingValues(16.dp)
        ) {
            // Her bir kampanyayı CampaignCard bileşeni ile göster
            items(campaigns) { campaign ->
                CampaignCard(
                    campaign = campaign,
                    isSelected = selectedCampaignId == campaign.id, // Seçili durumu belirlemek için
                    onApply = {
                        // Kampanyayı uygula ve sepet ekranına yönlendir
                        campaignViewModel.selectCampaign(campaign.id)
                        navController.navigate("cart?discount=${campaign.discount}&campaignId=${campaign.id}") {
                            popUpTo("cart") { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onRemove = {
                        // Kampanya seçimini kaldır ve sepet ekranına sıfırlanmış kampanya ile yönlendir
                        campaignViewModel.clearSelection()
                        navController.navigate("cart?discount=0&campaignId=-1") {
                            popUpTo("cart") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CampaignCard(
    campaign: CampaignData,
    isSelected: Boolean,
    onApply: () -> Unit,
    onRemove: () -> Unit
) {
    // Kampanya kartı tasarımı
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(BackGroundColor),
        shape = RoundedCornerShape(12.dp), // Kartın köşeleri yuvarlatıldı
        elevation = CardDefaults.cardElevation(4.dp) // Kartın gölge seviyesi
    ) {
        Row(
            modifier = Modifier.fillMaxSize().background(DarkBlue), // Kartın genel arka plan rengi
            verticalAlignment = Alignment.CenterVertically // İçeriklerin dikey ortalanması
        ) {
            // Kampanya görseli
            Image(
                painter = painterResource(id = campaign.imageRes),
                contentDescription = campaign.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .clip(RoundedCornerShape(12.dp)) // Görsel köşeleri yuvarlatıldı
            )

            Spacer(modifier = Modifier.width(8.dp)) // Görsel ile metin arasında boşluk

            // Kampanya başlığı ve detayları
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = campaign.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp)) // Başlık ve detaylar arasında boşluk
                Text(
                    text = campaign.details,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(8.dp)) // Metin ile buton arasında boşluk

            // Kampanya uygulama/kaldırma butonu
            Button(
                onClick = { if (isSelected) onRemove() else onApply() }, // Seçim durumuna göre aksiyon
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color.White else BackGroundColor, // Buton rengi
                    contentColor = if (isSelected) Red else Color.White // Buton içeriği rengi
                ),
                border = if (isSelected) {
                    BorderStroke(1.dp, Red) // Seçili durumunda kırmızı kenarlık
                } else {
                    null
                },
                shape = RoundedCornerShape(4.dp), // Buton köşeleri
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = if (isSelected) "Kaldır" else "Uygula") // Buton yazısı
            }
        }
    }
}

