package com.example.weatherapp.model

import android.content.Context
import androidx.room.*

//@Dao
//interface ProductsDAO{
//
//
//
//}
//@Database(entities = arrayOf(Products::class), version = 1 )
//abstract class ProductsDataBase : RoomDatabase() {
//    abstract fun getProductDao(): ProductsDAO
//    companion object{
//        @Volatile
//        private var INSTANCE: ProductsDataBase? = null
//        fun getInstance (ctx: Context): ProductsDataBase{
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    ctx.applicationContext, ProductsDataBase::class.java, "database")
//                    .build()
//                INSTANCE = instance
//
//                instance }
//        }
//    }
//}