package com.example.appproyectofinal.model

data class ItemCarrito(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: Int,
    var cantidad: Int = 1
)

