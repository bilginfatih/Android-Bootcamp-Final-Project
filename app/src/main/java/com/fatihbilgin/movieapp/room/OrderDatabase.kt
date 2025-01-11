package com.fatihbilgin.movieapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fatihbilgin.movieapp.data.entity.OrderData

@Database(entities = [OrderData::class], version = 1, exportSchema = false)
abstract class OrderDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
}
