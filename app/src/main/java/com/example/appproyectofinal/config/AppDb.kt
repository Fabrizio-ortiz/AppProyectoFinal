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

@Database(entities = [User::class, PedidoEntity::class], version = 3)
abstract class AppDb : RoomDatabase() {
    abstract  fun userDao(): UserDao
    abstract fun pedidoDao(): PedidoDao

    companion object{
        @Volatile private var INSTANCE: AppDb? = null

        // Migración de la versión 1 a la versión 2
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Asegúrate de agregar las nuevas columnas a la tabla pedidos
                database.execSQL("ALTER TABLE pedidos ADD COLUMN delivery INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE pedidos ADD COLUMN fecha TEXT NOT NULL DEFAULT ''")

            }
        }

        fun getDatabase(context: Context): AppDb{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "mi_base_de_datos"

                )   .addMigrations(MIGRATION_2_3) // Agrega la migración aquí
                    .fallbackToDestructiveMigration() // Solo para desarrollo, eliminará la base de datos al cambiar el esquema
                    .build()
                INSTANCE = instance
                instance

            }
        }

    }
}