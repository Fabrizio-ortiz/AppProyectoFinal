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


// Adaptador para mostrar una lista de Entradas en un RecyclerView
class EntradaAdapter(private val listaEntrada: List<Entrada>) :
    RecyclerView.Adapter<EntradaAdapter.EntradaViewHolder>() {

    // ViewHolder: representa un solo ítem visual de la lista (una entrada)
    class EntradaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nombre: TextView = itemView.findViewById(R.id.txtNombre)     // Nombre de la entrada
        val precio: TextView = itemView.findViewById(R.id.txtPrecio)     // Precio de la entrada
        val imagen: ImageView = itemView.findViewById(R.id.imgPlato)     // Imagen de la entrada
        val botonOrdenar: Button = itemView.findViewById(R.id.btnOrdenar) // Botón para agregar al carrito
    }

    // Se ejecuta cuando se necesita crear un nuevo ViewHolder (ítem visual en pantalla)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntradaViewHolder {
        // Inflamos la vista desde el layout item_entradas.xml
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_entradas, parent, false)
        return EntradaViewHolder(vista)
    }

    // Se ejecuta cada vez que se necesita llenar datos en un ViewHolder ya existente
    override fun onBindViewHolder(holder: EntradaViewHolder, position: Int) {
        val entrada = listaEntrada[position]

        // Asignamos los datos de la entrada al layout
        holder.nombre.text = entrada.nombreEntrada
        holder.precio.text = "S/ ${String.format("%.2f", entrada.precio)}"
        holder.imagen.setImageResource(entrada.imagen)

        // Configuramos el botón "Ordenar"
        holder.botonOrdenar.setOnClickListener {
            Carrito.agregar(entrada) // Agrega la entrada al carrito usando el método de Carrito
            Toast.makeText(holder.itemView.context, "${entrada.nombreEntrada} agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    // Retorna la cantidad de ítems que hay en la lista (para que RecyclerView sepa cuántos mostrar)
    override fun getItemCount() = listaEntrada.size
}
