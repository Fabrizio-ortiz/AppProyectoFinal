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
import com.example.appproyectofinal.model.Entrada


class EntradaAdapter(private val listaEntrada: List<Entrada>) :
    RecyclerView.Adapter<EntradaAdapter.EntradaViewHolder>() {

    class EntradaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nombre: TextView = itemView.findViewById(R.id.txtNombre)
        val precio: TextView = itemView.findViewById(R.id.txtPrecio)
        val imagen: ImageView = itemView.findViewById(R.id.imgPlato)
        val botonOrdenar: Button = itemView.findViewById(R.id.btnOrdenar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntradaViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_entradas, parent, false)
        return EntradaViewHolder(vista)
    }

    override fun onBindViewHolder(holder: EntradaViewHolder, position: Int) {
        val entrada = listaEntrada[position]
        holder.nombre.text = entrada.nombreEntrada
        holder.precio.text = "S/ ${String.format("%.2f", entrada.precio)}"
        holder.imagen.setImageResource(entrada.imagen)

        holder.botonOrdenar.setOnClickListener {
            Carrito.agregar(entrada)
            Toast.makeText(holder.itemView.context, "${entrada.nombreEntrada} agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = listaEntrada.size
}