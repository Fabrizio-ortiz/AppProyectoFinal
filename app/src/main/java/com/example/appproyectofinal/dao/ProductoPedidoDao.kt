package com.example.appproyectofinal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appproyectofinal.model.ProductoPedidoEntity

@Dao
interface ProductoPedidoDao{

    @Insert
    suspend fun insertarProducto(producto: ProductoPedidoEntity)

    @Query("SELECT * FROM productos_pedido WHERE numeroPedido = :numeroPedido")
    suspend fun obtenerProductosPorNumeroPedido(numeroPedido: String): List<ProductoPedidoEntity>
}
