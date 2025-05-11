package com.example.appproyectofinal.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appproyectofinal.dao.PedidoDao
import com.example.appproyectofinal.dao.UserDao
import com.example.appproyectofinal.model.PedidoEntity
import com.example.appproyectofinal.model.User

@Database(entities = [User::class, PedidoEntity::class], version = 4)
abstract class AppDb : RoomDatabase() {
    abstract  fun userDao(): UserDao
    abstract fun pedidoDao(): PedidoDao

    companion object{
        @Volatile private var INSTANCE: AppDb? = null

        // Migración de la versión 1 a la versión 2
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Añadir el nuevo campo `numeroPedido`
                database.execSQL("ALTER TABLE pedidos ADD COLUMN numeroPedido TEXT NOT NULL DEFAULT ''")
            }
        }


        fun getDatabase(context: Context): AppDb{
            context.deleteDatabase("mi_base_de_datos")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "mi_base_de_datos"

                )   .addMigrations(MIGRATION_3_4) // Agrega la migración aquí
                    .fallbackToDestructiveMigration() // Solo para desarrollo, eliminará la base de datos al cambiar el esquema
                    .build()
                INSTANCE = instance
                instance

            }
        }

    }
}