package com.example.appproyectofinal.dao

import com.example.appproyectofinal.model.Bebida
import com.example.appproyectofinal.model.Entrada
import com.example.appproyectofinal.model.ItemCarrito
import com.example.appproyectofinal.model.PlatosFuertes
import com.example.appproyectofinal.model.Postre

object Carrito {
    val items = mutableListOf<ItemCarrito>()

    fun agregar(plato: PlatosFuertes) {
        val existente = items.find { it.nombre == plato.nombrePlatoFuerte }
        if (existente != null) {
            existente.cantidad++
        } else {
            items.add(
                ItemCarrito(
                    nombre = plato.nombrePlatoFuerte,
                    descripcion = plato.descripcion,
                    precio = plato.precio,
                    imagen = plato.imagen
                )
            )
        }
    }

    fun agregar(entrada: Entrada) {
        val existente = items.find { it.nombre == entrada.nombreEntrada }
        if (existente != null) {
            existente.cantidad++
        } else {
            items.add(
                ItemCarrito(
                    nombre = entrada.nombreEntrada,
                    descripcion = "Sin descripción", // o algún texto genérico
                    precio = entrada.precio,
                    imagen = entrada.imagen
                )
            )
        }
    }

    fun agregar(postre: Postre) {
        val existente = items.find { it.nombre == postre.nombrePostre }
        if (existente != null) {
            existente.cantidad++
        } else {
            items.add(
                ItemCarrito(
                    nombre = postre.nombrePostre,
                    descripcion = "Sin descripción", // puedes personalizar si quieres
                    precio = postre.precio,
                    imagen = postre.imagen
                )
            )
        }
    }

    fun agregar(bebida: Bebida) {
        val existente = items.find { it.nombre == bebida.nombreBebida }
        if (existente != null) {
            existente.cantidad++
        } else {
            items.add(
                ItemCarrito(
                    nombre = bebida.nombreBebida,
                    descripcion = bebida.descripcion,
                    precio = bebida.precio,
                    imagen = bebida.imagen
                )
            )
        }
    }


    fun limpiar() {
        items.clear()
    }

    // Método para eliminar un ítem del carrito
    fun eliminar(item: ItemCarrito) {
        items.remove(item)
    }
}