package com.example.appproyectofinal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appproyectofinal.model.PedidoEntity

@Dao
interface PedidoDao {

    @Insert
    suspend fun insertarPedido(pedido: PedidoEntity)

    @Query("SELECT * FROM pedidos")
    suspend fun obtenerTodos(): List<PedidoEntity>

    @Query("DELETE FROM pedidos")
    suspend fun eliminarTodos()
}
