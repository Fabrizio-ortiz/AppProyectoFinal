package com.example.appproyectofinal.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appproyectofinal.dao.UserDao
import com.example.appproyectofinal.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract  fun userDao(): UserDao

    companion object{
        @Volatile private var INSTANCE: AppDb? = null

        fun getDatabase(context: Context): AppDb{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "mi_base_de_datos"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}