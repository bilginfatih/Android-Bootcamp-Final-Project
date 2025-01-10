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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.R
import com.fatihbilgin.movieapp.data.entity.CampaignData
import com.fatihbilgin.movieapp.ui.theme.BackGroundColor
import com.fatihbilgin.movieapp.ui.theme.DarkBlue
import com.fatihbilgin.movieapp.ui.theme.Red
import com.fatihbilgin.movieapp.ui.viewmodel.CampaignViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignScreen(
    navController: NavController,
    initialCampaignId: Int?,
    campaignViewModel: CampaignViewModel = hiltViewModel()
) {

    LaunchedEffect(initialCampaignId) {
        if (initialCampaignId != null && initialCampaignId != -1) {
            campaignViewModel.selectCampaign(initialCampaignId)
        }
    }

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

    val selectedCampaignId by campaignViewModel.selectedCampaignId.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Kampanyalar", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBlue),
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackGroundColor),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(campaigns) { campaign ->
                CampaignCard(
                    campaign = campaign,
                    isSelected = selectedCampaignId == campaign.id,
                    onApply = {
                        campaignViewModel.selectCampaign(campaign.id)
                        navController.navigate("cart?discount=${campaign.discount}&campaignId=${campaign.id}") {
                            popUpTo("cart") { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onRemove = {
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(BackGroundColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().background(DarkBlue),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = campaign.imageRes),
                contentDescription = campaign.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

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
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = campaign.details,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { if (isSelected) onRemove() else onApply() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color.White else BackGroundColor,
                    contentColor = if (isSelected) Red else Color.White
                ),
                border = if (isSelected) {
                    BorderStroke(1.dp, Red)
                } else {
                    null
                },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = if (isSelected) "Kaldır" else "Uygula")
            }
        }
    }
}


