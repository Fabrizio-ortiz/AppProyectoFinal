import android.content.Context
import com.example.appproyectofinal.config.AppDb
import com.example.appproyectofinal.model.Bebida
import com.example.appproyectofinal.model.Entrada
import com.example.appproyectofinal.model.ItemCarrito
import com.example.appproyectofinal.model.PlatosFuertes
import com.example.appproyectofinal.model.Postre
import com.example.appproyectofinal.model.PedidoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    // Hacer que esta función sea 'suspend' para usarla en un hilo de fondo
    suspend fun realizarPedido(context: Context, email: String, metodoEnvio: String) {
        // Obtén la instancia de la base de datos utilizando el contexto
        val pedidoDao = AppDb.getDatabase(context).pedidoDao()

        // Crear el objeto de PedidoEntity
        val precioTotal = items.sumOf { it.precio * it.cantidad }
        val fecha = "2025-05-11" // Cambia por la fecha actual

        val pedido = PedidoEntity(
            nombrePlato = "Plato de ejemplo",  // O lo que corresponda
            cantidad = 2,                      // Lo que corresponda
            precioTotal = precioTotal,
            fecha = fecha,
            delivery = 5.0,                   // Valor fijo de delivery
            metodoEnvio = metodoEnvio,
            numeroPedido = "12345",           // Número de pedido generado
            email = email                      // Relacionado con el usuario
        )

        // Insertar el pedido en la base de datos usando conContext para asegurar que se ejecute en un hilo de fondo
        withContext(Dispatchers.IO) {
            pedidoDao.insertarPedido(pedido)
        }
    }
}
