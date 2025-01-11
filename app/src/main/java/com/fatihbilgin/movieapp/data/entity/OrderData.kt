package com.fatihbilgin.movieapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order") // Veritabanında "order" adında bir tablo oluşturur
data class OrderData(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val userName: String,
    val orderDate: Long, // Timestamp formatında tarih
    val totalPrice: Double,
    val discount: Int,
    val finalPrice: Double,
    val cartItems: String // JSON formatında ürün listesi
)
