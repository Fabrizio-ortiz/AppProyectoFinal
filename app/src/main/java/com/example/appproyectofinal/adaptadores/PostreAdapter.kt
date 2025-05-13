package com.example.appproyectofinal.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.R
import com.example.appproyectofinal.model.Postre

// Adaptador para mostrar una lista de postres en un RecyclerView
class PostreAdapter(private val listaPostre: List<Postre>) :
    RecyclerView.Adapter<PostreAdapter.PostreViewHolder>() {

    // ViewHolder: clase interna que representa cada ítem (vista) del RecyclerView
    class PostreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.txtNombre)      // Nombre del postre
        val precio: TextView = itemView.findViewById(R.id.txtPrecio)      // Precio del postre
        val imagen: ImageView = itemView.findViewById(R.id.imgPlato)      // Imagen del postre
        val botonOrdenar: Button = itemView.findViewById(R.id.btnOrdenar) // Botón para agregar al carrito
    }

    // Método que infla el layout del ítem y crea un nuevo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostreViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_postres, parent, false)
        return PostreViewHolder(vista)
    }

    // Método que se llama por cada ítem para mostrar los datos en pantalla
    override fun onBindViewHolder(holder: PostreViewHolder, position: Int) {
        val postre = listaPostre[position] // Obtiene el postre actual

        // Asigna los valores del postre a los elementos del layout
        holder.nombre.text = postre.nombrePostre
        holder.precio.text = "S/ ${String.format("%.2f", postre.precio)}"
        holder.imagen.setImageResource(postre.imagen)

        // Acción del botón para agregar al carrito
        holder.botonOrdenar.setOnClickListener {
            Carrito.agregar(postre) // Llama al método del objeto Carrito para agregar el postre
            Toast.makeText(holder.itemView.context, "${postre.nombrePostre} agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    // Retorna la cantidad de elementos en la lista de postres
    override fun getItemCount() = listaPostre.size
}

