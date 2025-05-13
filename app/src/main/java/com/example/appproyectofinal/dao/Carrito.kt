// Importaciones de los modelos que pueden ser agregados al carrito
import com.example.appproyectofinal.model.Bebida
import com.example.appproyectofinal.model.Entrada
import com.example.appproyectofinal.model.ItemCarrito
import com.example.appproyectofinal.model.PlatosFuertes
import com.example.appproyectofinal.model.Postre

// Objeto singleton que representa el carrito de compras
object Carrito {

    // Lista mutable que almacena los ítems actuales del carrito
    val items = mutableListOf<ItemCarrito>()

    // Método para agregar un plato fuerte al carrito
    fun agregar(plato: PlatosFuertes) {
        // Busca si el plato ya está en el carrito
        val existente = items.find { it.nombre == plato.nombrePlatoFuerte }
        if (existente != null) {
            // Si ya existe, incrementa la cantidad
            existente.cantidad++
        } else {
            // Si no existe, lo agrega como nuevo ítem
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

    // Método para agregar una entrada al carrito
    fun agregar(entrada: Entrada) {
        val existente = items.find { it.nombre == entrada.nombreEntrada }
        if (existente != null) {
            existente.cantidad++
        } else {
            items.add(
                ItemCarrito(
                    nombre = entrada.nombreEntrada,
                    descripcion = "Sin descripción",
                    precio = entrada.precio,
                    imagen = entrada.imagen
                )
            )
        }
    }

    // Método para agregar un postre al carrito
    fun agregar(postre: Postre) {
        val existente = items.find { it.nombre == postre.nombrePostre }
        if (existente != null) {
            existente.cantidad++
        } else {
            items.add(
                ItemCarrito(
                    nombre = postre.nombrePostre,
                    descripcion = "Sin descripción",
                    precio = postre.precio,
                    imagen = postre.imagen
                )
            )
        }
    }

    // Método para agregar una bebida al carrito
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

    // Método para vaciar completamente el carrito
    fun limpiar() {
        items.clear()
    }

    // Método para eliminar un ítem específico del carrito
    fun eliminar(item: ItemCarrito) {
        items.remove(item)
    }
}
