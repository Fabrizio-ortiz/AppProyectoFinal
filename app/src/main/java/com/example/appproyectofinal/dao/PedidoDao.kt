package com.example.appproyectofinal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appproyectofinal.model.PedidoEntity
import com.example.appproyectofinal.model.ProductoPedidoEntity

@Dao
interface PedidoDao {

    @Insert
    suspend fun insertarPedido(pedido: PedidoEntity)

    @Query("SELECT * FROM pedidos ORDER BY id DESC LIMIT 1")
    suspend fun obtenerUltimoPedido(): PedidoEntity?

    @Query("SELECT * FROM pedidos WHERE email = :email")
    suspend fun obtenerPedidosPorEmail(email: String): List<PedidoEntity>

    @Query("SELECT * FROM pedidos WHERE email = :email ORDER BY id desc,fecha DESC")
    suspend fun obtenerPedidosPorUsuario(email: String): List<PedidoEntity>

    // Cambié esta consulta para obtener los productos relacionados con un pedido
    @Query("SELECT * FROM productos_pedido WHERE numeroPedido = :numeroPedido")
    suspend fun obtenerProductosPorNumeroPedido(numeroPedido: String): List<ProductoPedidoEntity>

    @Query("SELECT * FROM pedidos")
    suspend fun obtenerTodos(): List<PedidoEntity>

    @Query("DELETE FROM pedidos")
    suspend fun eliminarTodos()
}

