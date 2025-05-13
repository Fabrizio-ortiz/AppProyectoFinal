package com.example.appproyectofinal.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.R
import com.example.appproyectofinal.model.PedidoEntity

// Adaptador para mostrar una lista de pedidos en el historial
class HistorialPedidoAdapter(
    private val listaPedidos: List<PedidoEntity>, // Lista de pedidos a mostrar
    private val onClick: (PedidoEntity) -> Unit   // Función lambda que se ejecuta al hacer clic en un pedido
) : RecyclerView.Adapter<HistorialPedidoAdapter.PedidoViewHolder>() {

    // Crea la vista de cada item del RecyclerView (inflate del layout XML)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historial_pedido, parent, false)
        return PedidoViewHolder(view)
    }

    // Enlaza cada pedido de la lista con la vista del ViewHolder
    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = listaPedidos[position]
        holder.bind(pedido) // Llama al método que llena los campos de texto con los datos del pedido
    }

    // Devuelve el número total de ítems (pedidos) en la lista
    override fun getItemCount(): Int = listaPedidos.size

    // ViewHolder interno que representa cada ítem del historial de pedidos
    inner class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Referencias a los TextView definidos en item_historial_pedido.xml
        private val txtNumeroPedido: TextView = itemView.findViewById(R.id.txtNumeroPedido)
        private val txtFecha: TextView = itemView.findViewById(R.id.txtFecha)
        private val txtTotal: TextView = itemView.findViewById(R.id.txtTotal)

        // Método para vincular los datos del pedido con los elementos visuales
        fun bind(pedido: PedidoEntity) {
            txtNumeroPedido.text = pedido.numeroPedido // Muestra el número del pedido
            txtFecha.text = pedido.fecha               // Muestra la fecha del pedido
            txtTotal.text = "S/ %.2f".format(pedido.precioTotal) // Muestra el total formateado

            // Asigna un evento al hacer clic sobre el ítem completo
            itemView.setOnClickListener {
                onClick(pedido) // Llama a la función recibida en el constructor con el pedido clicado
            }
        }
    }
}

