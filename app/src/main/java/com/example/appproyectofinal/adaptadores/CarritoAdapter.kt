package com.example.appproyectofinal.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.R
import com.example.appproyectofinal.dao.Carrito
import com.example.appproyectofinal.model.ItemCarrito

class CarritoAdapter(
    private val items: MutableList<ItemCarrito>,
    private val onCambios: () -> Unit
) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.txtNombre)
        val cantidad: TextView = view.findViewById(R.id.txtCantidad)
        val precio: TextView = view.findViewById(R.id.txtPrecio)
        val imagen: ImageView = view.findViewById(R.id.imgPlato)
        val btnIncrementar: Button = view.findViewById(R.id.btnIncrementar)
        val btnDecrementar: Button = view.findViewById(R.id.btnDecrementar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nombre.text = item.nombre
        holder.cantidad.text = "${item.cantidad}"
        holder.precio.text = "S/ %.2f".format(item.precio * item.cantidad)
        holder.imagen.setImageResource(item.imagen)

        holder.btnIncrementar.setOnClickListener {
            item.cantidad++
            notifyItemChanged(position)
            onCambios()
        }

        holder.btnDecrementar.setOnClickListener {
            if (item.cantidad > 1) {
                item.cantidad--
                notifyItemChanged(position)
                onCambios()
            }
        }

        holder.btnEliminar.setOnClickListener {
            val item = items[position]
            Carrito.eliminar(item)  // Eliminar el item de la lista global
            items.removeAt(position) // Eliminar el item de la lista local (del RecyclerView)
            notifyItemRemoved(position) // Notificar al RecyclerView que el item ha sido eliminado
            notifyItemRangeChanged(position, items.size) // Actualizar el rango de la lista
            onCambios() // Llamar a la función para actualizar el total
        }
    }

    override fun getItemCount() = items.size
}
