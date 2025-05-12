package com.example.appproyectofinal.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appproyectofinal.dao.PedidoDao
import com.example.appproyectofinal.dao.ProductoPedidoDao
import com.example.appproyectofinal.dao.UserDao
import com.example.appproyectofinal.model.PedidoEntity
import com.example.appproyectofinal.model.ProductoPedidoEntity
import com.example.appproyectofinal.model.User

@Database(entities = [User::class, PedidoEntity::class, ProductoPedidoEntity::class], version = 7) // Agregamos ProductoPedidoEntity y actualizamos la versión
abstract class AppDb : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun productoDao(): ProductoPedidoDao // Agregar este DAO

    companion object {
        @Volatile private var INSTANCE: AppDb? = null


        fun getDatabase(context: Context): AppDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "mi_base_de_datos"
                )
                    .fallbackToDestructiveMigration() // Eliminar base de datos si es necesario durante desarrollo
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


