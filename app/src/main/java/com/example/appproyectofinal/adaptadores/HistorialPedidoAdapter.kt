package com.example.appproyectofinal.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.R
import com.example.appproyectofinal.model.PedidoEntity

class HistorialPedidoAdapter(
    private val listaPedidos: List<PedidoEntity>,
    private val onClick: (PedidoEntity) -> Unit
) : RecyclerView.Adapter<HistorialPedidoAdapter.PedidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historial_pedido, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = listaPedidos[position]
        holder.bind(pedido)
    }

    override fun getItemCount(): Int = listaPedidos.size

    inner class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtNumeroPedido: TextView = itemView.findViewById(R.id.txtNumeroPedido)
        private val txtFecha: TextView = itemView.findViewById(R.id.txtFecha)
        private val txtTotal: TextView = itemView.findViewById(R.id.txtTotal)

        fun bind(pedido: PedidoEntity) {
            txtNumeroPedido.text = pedido.numeroPedido
            txtFecha.text = pedido.fecha
            txtTotal.text = "S/ %.2f".format(pedido.precioTotal)

            // Al hacer clic en el item, se invoca el callback para navegar al detalle del pedido
            itemView.setOnClickListener {
                onClick(pedido)
            }
        }
    }
}

