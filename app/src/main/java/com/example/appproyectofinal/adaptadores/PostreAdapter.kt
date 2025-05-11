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
import com.example.appproyectofinal.dao.Carrito
import com.example.appproyectofinal.model.Postre

class PostreAdapter(private val listaPostre: List<Postre>) :
    RecyclerView.Adapter<PostreAdapter.PostreViewHolder>() {

    class PostreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nombre: TextView = itemView.findViewById(R.id.txtNombre)
        val precio: TextView = itemView.findViewById(R.id.txtPrecio)
        val imagen: ImageView = itemView.findViewById(R.id.imgPlato)
        val botonOrdenar: Button = itemView.findViewById(R.id.btnOrdenar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostreViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_postres, parent, false)
        return PostreViewHolder(vista)
    }

    override fun onBindViewHolder(holder: PostreViewHolder, position: Int) {
        val postre = listaPostre[position]
        holder.nombre.text = postre.nombrePostre
        holder.precio.text = "S/ ${String.format("%.2f", postre.precio)}"
        holder.imagen.setImageResource(postre.imagen)

        holder.botonOrdenar.setOnClickListener {
            Carrito.agregar(postre)
            Toast.makeText(holder.itemView.context, "${postre.nombrePostre} agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = listaPostre.size
}
