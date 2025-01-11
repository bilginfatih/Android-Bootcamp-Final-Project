package com.fatihbilgin.movieapp.ui.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.data.entity.OrderData
import com.fatihbilgin.movieapp.ui.components.CommonTopAppBar
import com.fatihbilgin.movieapp.ui.theme.BackGroundColor
import com.fatihbilgin.movieapp.ui.theme.Red
import com.fatihbilgin.movieapp.ui.viewmodel.OrderViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun OrderScreen(orderViewModel: OrderViewModel, userName: String, navController: NavController) {
    val orders = remember { mutableStateOf(emptyList<OrderData>()) }
    val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Siparişleri yükle
    LaunchedEffect(Unit) {
        orders.value = orderViewModel.getOrders(userName)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            CommonTopAppBar(
                title = "Siparişlerim",
                navController = navController,
                actions = {
                    IconButton(onClick = {}) {}
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackGroundColor)
        ) {
            items(orders.value) { order ->
                val isExpanded = expandedStates[order.orderId] ?: false

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .clickable {
                            // Detay kartının durumunu güncelle
                            expandedStates[order.orderId] = !isExpanded
                        },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val formatter = DecimalFormat("#,##0.00")
                            val formattedTotalPrice = formatter.format(order.totalPrice).replace(".", ",") // Noktayı virgüle çevir
                            val formattedFinalPrice = formatter.format(order.finalPrice).replace(".", ",") // Noktayı virgüle çevir
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "Sipariş Tarihi: ${SimpleDateFormat("dd/MM/yyyy").format(Date(order.orderDate))}")
                                Text(text = "Toplam Fiyat: $formattedTotalPrice")
                                Text(text = if (order.discount == 0) "İndirim: Yok" else "İndirim: %${order.discount}")
                                Text(text = "Son Fiyat: $formattedFinalPrice")
                            }
                            // Silme butonu
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "Siparişinizi silmek istiyor musunuz?",
                                            actionLabel = "Evet"
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            // Siparişi sil
                                            orderViewModel.deleteOrder(order.orderId)
                                            orders.value -= order // Listeden kaldır
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Order",
                                    tint = Red
                                )
                            }
                        }
                        // Detay kartı
                        if (isExpanded) {
                            OrderDetailsCard(order.cartItems)
                        }
                    }

                    // En alta ortalanmış Arrow Down Image
                    if (!isExpanded) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expand",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }

}
