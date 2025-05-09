package com.example.appproyectofinal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "platosFuertes")
data class PlatosFuertes(
    @PrimaryKey(autoGenerate = true) val idPlatoFuerte: Long = 0,
    val nombrePlatoFuerte: String,
    val descripcion: String,
    val precio: Double,
    val imagen: Int
)