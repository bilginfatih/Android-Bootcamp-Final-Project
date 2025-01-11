package com.fatihbilgin.movieapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.fatihbilgin.movieapp.data.entity.OrderData
import com.fatihbilgin.movieapp.room.OrderDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val orderDao: OrderDao) : ViewModel() {

    suspend fun addOrder(order: OrderData) {
        withContext(Dispatchers.IO) {
            orderDao.insertOrder(order)
        }
    }

    suspend fun getOrders(userName: String): List<OrderData> {
        return withContext(Dispatchers.IO) {
            orderDao.getOrdersByUser(userName)
        }
    }

    suspend fun deleteOrder(orderId: Int) {
        withContext(Dispatchers.IO) {
            orderDao.deleteOrder(orderId)
        }
    }

}
