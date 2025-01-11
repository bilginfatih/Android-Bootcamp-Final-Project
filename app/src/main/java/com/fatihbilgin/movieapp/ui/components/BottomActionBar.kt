package com.fatihbilgin.movieapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.R
import com.fatihbilgin.movieapp.ui.theme.DarkBlue
import com.fatihbilgin.movieapp.ui.viewmodel.CartScreenViewModel

@Composable
fun BottomActionBar(cartItemCount: Int, navController: NavController, cartScreenViewModel: CartScreenViewModel) {
    val totalOrderAmount = cartScreenViewModel.totalOrderAmount.observeAsState(0)
    val _totalOrderAmount = totalOrderAmount.value
    val totalOrder = _totalOrderAmount + cartItemCount

    Box(modifier = Modifier.fillMaxSize()) {
    Box(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.1f) // Fixed height
            .padding(bottom = 18.dp)
            .background(DarkBlue, shape = RoundedCornerShape(24.dp))
            .clickable(enabled = false) {  }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Outlined.Home,
                contentDescription = "Home",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navController.navigate("homePage?cartCount=$cartItemCount") {
                            popUpTo("homePage") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
            )

            // BadgeBox for cart
            if (totalOrder > 0) {
                BadgedBox(
                    badge = {
                        Badge {
                            Text(text = totalOrder.toString()) // Show cart count
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_3),
                        contentDescription = "Add to Cart",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                            .clickable { navController.navigate("cart") }
                    )
                }
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.btn_3),
                    contentDescription = "Add to Cart",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                        .clickable { navController.navigate("cart") }
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search",
                tint = Color.White,
                modifier = Modifier
                    .size(26.dp)
                    .clickable { navController.navigate("searchScreen") }
            )
        }
    }
    }
}