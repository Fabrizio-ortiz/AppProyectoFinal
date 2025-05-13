// Paquete donde está esta clase
package com.example.appproyectofinal.config

// Importaciones necesarias para trabajar con Room, migraciones y DAOs
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

// Anotación que define la configuración de la base de datos Room
// - entities: las clases que representan las tablas de la base de datos
// - version: número de versión de la base de datos (importante para migraciones)
@Database(entities = [User::class, PedidoEntity::class, ProductoPedidoEntity::class], version = 8)
abstract class AppDb : RoomDatabase() {

    // Declaración abstracta de los DAOs que se usarán para acceder a los datos
    abstract fun userDao(): UserDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun productoDao(): ProductoPedidoDao // DAO para manejar la tabla ProductoPedidoEntity

    companion object {
        // INSTANCE es una referencia única de la base de datos (patrón Singleton)
        @Volatile private var INSTANCE: AppDb? = null

        // Método para obtener la instancia de la base de datos
        fun getDatabase(context: Context): AppDb {
            // Si ya existe una instancia, la retorna. Si no, la crea de manera segura (synchronized)
            return INSTANCE ?: synchronized(this) {
                // Se crea la base de datos usando Room.databaseBuilder
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Se usa el contexto de la aplicación
                    AppDb::class.java,          // Clase de la base de datos
                    "mi_base_de_datos"          // Nombre físico de la base de datos en el dispositivo
                )
                    // Si hay un cambio en la estructura y no se define una migración, elimina la base de datos y la crea de nuevo
                    // ¡Úsalo solo en desarrollo! En producción puedes perder los datos del usuario
                    .fallbackToDestructiveMigration()
                    .build()

                // Se guarda la instancia para usos posteriores
                INSTANCE = instance
                instance
            }
        }
    }
}


