package com.example.appproyectofinal.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appproyectofinal.dao.PedidoDao
import com.example.appproyectofinal.dao.UserDao
import com.example.appproyectofinal.model.PedidoEntity
import com.example.appproyectofinal.model.User

@Database(entities = [User::class, PedidoEntity::class], version = 2)
abstract class AppDb : RoomDatabase() {
    abstract  fun userDao(): UserDao
    abstract fun pedidoDao(): PedidoDao

    companion object{
        @Volatile private var INSTANCE: AppDb? = null

        fun getDatabase(context: Context): AppDb{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "mi_base_de_datos"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }

    }
}