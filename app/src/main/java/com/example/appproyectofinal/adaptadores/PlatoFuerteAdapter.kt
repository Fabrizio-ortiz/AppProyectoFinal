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
import com.example.appproyectofinal.model.PlatosFuertes

class PlatoFuerteAdapter(private val listaPlatos: List<PlatosFuertes>) :
    RecyclerView.Adapter<PlatoFuerteAdapter.PlatoViewHolder>() {



    class PlatoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.txtNombre)
        val descripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        val precio: TextView = itemView.findViewById(R.id.txtPrecio)
        val imagen: ImageView = itemView.findViewById(R.id.imgPlato)
        val botonOrdenar: Button = itemView.findViewById(R.id.btnOrdenar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatoViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_platofuerte, parent, false)
        return PlatoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: PlatoViewHolder, position: Int) {
        val plato = listaPlatos[position]
        holder.nombre.text = plato.nombrePlatoFuerte
        holder.descripcion.text = plato.descripcion
        holder.precio.text = "S/ ${String.format("%.2f", plato.precio)}"
        holder.imagen.setImageResource(plato.imagen)

        holder.botonOrdenar.setOnClickListener {
            Carrito.agregar(plato)
            Toast.makeText(holder.itemView.context, "${plato.nombrePlatoFuerte} agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount() = listaPlatos.size


}
