package com.example.appproyectofinal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedidos")
data class PedidoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombrePlato: String,
    val cantidad: Int,
    val precioTotal: Double,
    val fecha: String,
    val delivery: Double,  // Costo de delivery (fijo o 0)
    val metodoEnvio: String,  // "Delivery" o "Recoger en tienda"
    val numeroPedido: String // Nuevo campo para el número de pedido
)