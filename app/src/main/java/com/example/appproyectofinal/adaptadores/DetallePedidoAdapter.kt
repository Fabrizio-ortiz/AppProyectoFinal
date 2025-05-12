package com.example.appproyectofinal.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.R
import com.example.appproyectofinal.model.ItemCarrito

class DetallePedidoAdapter(private val productos: List<ItemCarrito>) :
    RecyclerView.Adapter<DetallePedidoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreProducto: TextView = view.findViewById(R.id.txtNombreProducto)
        val cantidadProducto: TextView = view.findViewById(R.id.txtCantidadProducto)
        val precioProducto: TextView = view.findViewById(R.id.txtPrecioProducto)
        val imagenProducto: ImageView = view.findViewById(R.id.imgProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_pedido, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productos[position]
        holder.nombreProducto.text = item.nombre
        holder.cantidadProducto.text = "Cantidad: ${item.cantidad}"
        holder.precioProducto.text = "S/ %.2f".format(item.precio * item.cantidad)
        holder.imagenProducto.setImageResource(item.imagen)
    }

    override fun getItemCount(): Int = productos.size
}
