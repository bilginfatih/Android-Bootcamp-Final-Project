package com.fatihbilgin.movieapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fatihbilgin.movieapp.data.entity.OrderData

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrder(order: OrderData)

    @Query("SELECT * FROM `order` WHERE userName = :userName")
    suspend fun getOrdersByUser(userName: String): List<OrderData>

    @Query("DELETE FROM `order` WHERE orderId = :orderId")
    suspend fun deleteOrder(orderId: Int)

}
