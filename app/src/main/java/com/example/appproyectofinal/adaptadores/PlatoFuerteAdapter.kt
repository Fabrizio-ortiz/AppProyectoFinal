
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
import com.example.appproyectofinal.model.PlatosFuertes

// Clase adaptador para mostrar una lista de platos fuertes en un RecyclerView
class PlatoFuerteAdapter(private val listaPlatos: List<PlatosFuertes>) :
    RecyclerView.Adapter<PlatoFuerteAdapter.PlatoViewHolder>() {

    // ViewHolder: representa cada ítem (tarjeta) del RecyclerView
    class PlatoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Referencias a los elementos gráficos definidos en item_platofuerte.xml
        val nombre: TextView = itemView.findViewById(R.id.txtNombre)
        val descripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        val precio: TextView = itemView.findViewById(R.id.txtPrecio)
        val imagen: ImageView = itemView.findViewById(R.id.imgPlato)
        val botonOrdenar: Button = itemView.findViewById(R.id.btnOrdenar)
    }

    // Crea una nueva vista para un ítem cuando sea necesario
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatoViewHolder {
        // Infla (crea visualmente) el layout de cada ítem usando item_platofuerte.xml
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_platofuerte, parent, false)
        return PlatoViewHolder(vista)
    }

    // Asocia los datos de un plato fuerte con los elementos visuales del ViewHolder
    override fun onBindViewHolder(holder: PlatoViewHolder, position: Int) {
        // Obtiene el objeto de la lista en la posición actual
        val plato = listaPlatos[position]

        // Asigna los valores del plato a los componentes visuales
        holder.nombre.text = plato.nombrePlatoFuerte
        holder.descripcion.text = plato.descripcion
        holder.precio.text = "S/ ${String.format("%.2f", plato.precio)}" // Muestra el precio con dos decimales
        holder.imagen.setImageResource(plato.imagen) // Muestra la imagen correspondiente al recurso

        // Acción al hacer clic en el botón "Ordenar"
        holder.botonOrdenar.setOnClickListener {
            // Agrega el plato al carrito
            Carrito.agregar(plato)

            // Muestra un mensaje indicando que se agregó al carrito
            Toast.makeText(holder.itemView.context, "${plato.nombrePlatoFuerte} agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    // Devuelve la cantidad total de elementos que se mostrarán en el RecyclerView
    override fun getItemCount() = listaPlatos.size
}

