// Paquete en el que se encuentra esta clase
package com.example.appproyectofinal.adaptadores

// Importación de clases necesarias para el funcionamiento del adapter y vistas
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

// Clase adaptador para mostrar una lista de bebidas en un RecyclerView
class BebidaAdapter(private val listaBebida: List<Bebida>) :
    RecyclerView.Adapter<BebidaAdapter.BebidaViewHolder>() {

    // ViewHolder: mantiene las referencias a las vistas de cada item de la lista
    class BebidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // Referencias a los elementos gráficos del layout item_bebidas.xml
        val nombre: TextView = itemView.findViewById(R.id.txtNombre)
        val descripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        val precio: TextView = itemView.findViewById(R.id.txtPrecio)
        val imagen: ImageView = itemView.findViewById(R.id.imgPlato)
        val botonOrdenar: Button = itemView.findViewById(R.id.btnOrdenar)
    }

    // Se llama cuando el RecyclerView necesita crear una nueva vista (ViewHolder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BebidaViewHolder {
        // Infla (crea) la vista del ítem a partir del layout XML (item_bebidas.xml)
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_bebidas, parent, false)
        return BebidaViewHolder(vista)
    }

    // Se llama para mostrar los datos en una posición específica de la lista
    override fun onBindViewHolder(holder: BebidaViewHolder, position: Int) {
        // Obtiene el objeto Bebida que se va a mostrar
        val bebida = listaBebida[position]

        // Asigna los valores del objeto a los elementos de la vista
        holder.nombre.text = bebida.nombreBebida
        holder.descripcion.text = bebida.descripcion
        holder.precio.text = "S/ ${String.format("%.2f", bebida.precio)}" // Muestra el precio con dos decimales
        holder.imagen.setImageResource(bebida.imagen) // Muestra la imagen correspondiente

        // Maneja el evento de clic en el botón "Ordenar"
        holder.botonOrdenar.setOnClickListener {
            // Agrega la bebida al carrito (clase Carrito debería estar implementada en otra parte)
            Carrito.agregar(bebida)

            // Muestra un mensaje emergente (Toast) indicando que se agregó la bebida
            Toast.makeText(holder.itemView.context, "${bebida.nombreBebida} agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    // Devuelve la cantidad total de elementos en la lista
    override fun getItemCount() = listaBebida.size
}
