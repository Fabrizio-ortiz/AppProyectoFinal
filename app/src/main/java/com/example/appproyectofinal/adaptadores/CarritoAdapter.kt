package com.example.appproyectofinal.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.R
import com.example.appproyectofinal.model.ItemCarrito

// Adaptador para mostrar y gestionar los productos en el carrito de compras
class CarritoAdapter(
    private val items: MutableList<ItemCarrito>, // Lista de productos en el carrito
    private val onCambios: () -> Unit // Función callback que se llama cuando se modifica la lista (para actualizar el total, por ejemplo)
) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    // ViewHolder interno que representa cada ítem del carrito
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.txtNombre) // Nombre del producto
        val cantidad: TextView = view.findViewById(R.id.txtCantidad) // Cantidad seleccionada
        val precio: TextView = view.findViewById(R.id.txtPrecio) // Precio total (precio * cantidad)
        val imagen: ImageView = view.findViewById(R.id.imgPlato) // Imagen del producto

        // Botones para modificar el ítem
        val btnIncrementar: Button = view.findViewById(R.id.btnIncrementar) // Aumentar cantidad
        val btnDecrementar: Button = view.findViewById(R.id.btnDecrementar) // Disminuir cantidad
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar) // Eliminar del carrito
    }

    // Método que infla la vista del ítem desde el layout XML (item_carrito.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(vista)
    }

    // Método que asigna los datos del producto a los elementos visuales del ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // Asignamos los datos visuales al ítem
        holder.nombre.text = item.nombre
        holder.cantidad.text = "${item.cantidad}"
        holder.precio.text = "S/ %.2f".format(item.precio * item.cantidad)
        holder.imagen.setImageResource(item.imagen)

        // Aumentar cantidad
        holder.btnIncrementar.setOnClickListener {
            item.cantidad++ // Incrementamos la cantidad
            notifyItemChanged(position) // Notificamos que ese ítem ha cambiado
            onCambios() // Actualizamos totales u otros cambios en la UI
        }

        // Disminuir cantidad
        holder.btnDecrementar.setOnClickListener {
            if (item.cantidad > 1) {
                item.cantidad-- // Disminuimos la cantidad solo si es mayor que 1
                notifyItemChanged(position) // Notificamos el cambio
                onCambios()
            }
        }

        // Eliminar producto del carrito
        holder.btnEliminar.setOnClickListener {
            val item = items[position]
            Carrito.eliminar(item)  // Elimina el ítem de la lista global del carrito
            items.removeAt(position) // Elimina el ítem de la lista del RecyclerView
            notifyItemRemoved(position) // Notificamos que se eliminó un ítem
            notifyItemRangeChanged(position, items.size) // Notificamos los cambios de posición restantes
            onCambios() // Actualiza el total general u otros elementos relacionados
        }
    }

    // Retorna el número total de ítems en el carrito
    override fun getItemCount() = items.size
}
