package com.example.appproyectofinal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos_pedido")
data class ProductoPedidoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pedidoId: Int, // Relación con PedidoEntity
    val nombrePlato: String,
    val cantidad: Int,
    val precio: Double,
    val numeroPedido: String, // Este campo se relaciona con el número de pedido
    val imagenResId: Int
)