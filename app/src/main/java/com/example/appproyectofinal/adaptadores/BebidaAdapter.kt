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
import com.example.appproyectofinal.model.Bebida


class BebidaAdapter(private val listaBebida: List<Bebida>) :
    RecyclerView.Adapter<BebidaAdapter.BebidaViewHolder>() {

    class BebidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nombre: TextView = itemView.findViewById(R.id.txtNombre)
        val descripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        val precio: TextView = itemView.findViewById(R.id.txtPrecio)
        val imagen: ImageView = itemView.findViewById(R.id.imgPlato)
        val botonOrdenar: Button = itemView.findViewById(R.id.btnOrdenar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BebidaViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_bebidas, parent, false)
        return BebidaViewHolder(vista)
    }

    override fun onBindViewHolder(holder: BebidaViewHolder, position: Int) {
        val bebida = listaBebida[position]
        holder.nombre.text = bebida.nombreBebida
        holder.descripcion.text = bebida.descripcion
        holder.precio.text = "S/ ${String.format("%.2f", bebida.precio)}"
        holder.imagen.setImageResource(bebida.imagen)

        holder.botonOrdenar.setOnClickListener {
            Carrito.agregar(bebida)
            Toast.makeText(holder.itemView.context, "${bebida.nombreBebida} agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = listaBebida.size
}