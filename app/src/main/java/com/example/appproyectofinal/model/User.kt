package com.example.appproyectofinal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class User(
    @PrimaryKey(autoGenerate = true) val idEmpleado: Long = 0,
    val email: String,
    val password: String,
    val nombres: String,
    val apellidos: String,
    val celular: String
)