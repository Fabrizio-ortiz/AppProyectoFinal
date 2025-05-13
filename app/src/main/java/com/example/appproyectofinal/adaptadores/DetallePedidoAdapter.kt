package com.example.appproyectofinal.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.R
import com.example.appproyectofinal.model.ItemCarrito

// Adaptador para mostrar los productos incluidos en un pedido (detalle del pedido)
class DetallePedidoAdapter(private val productos: List<ItemCarrito>) :
    RecyclerView.Adapter<DetallePedidoAdapter.ViewHolder>() {

    // ViewHolder: representa la vista de cada producto dentro del pedido
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Referencias a los elementos visuales del layout item_producto_pedido.xml
        val nombreProducto: TextView = view.findViewById(R.id.txtNombreProducto)
        val cantidadProducto: TextView = view.findViewById(R.id.txtCantidadProducto)
        val precioProducto: TextView = view.findViewById(R.id.txtPrecioProducto)
        val imagenProducto: ImageView = view.findViewById(R.id.imgProducto)
    }

    // Se llama cuando se necesita crear una nueva vista (ViewHolder)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla la vista desde el archivo XML item_producto_pedido.xml
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_pedido, parent, false)
        return ViewHolder(vista)
    }

    // Se llama para mostrar los datos de un producto específico en su vista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Obtiene el producto actual a mostrar
        val item = productos[position]

        // Asigna los valores a los TextViews e ImageView
        holder.nombreProducto.text = item.nombre
        holder.cantidadProducto.text = "Cantidad: ${item.cantidad}"
        holder.precioProducto.text = "S/ %.2f".format(item.precio * item.cantidad) // Precio total por ese producto
        holder.imagenProducto.setImageResource(item.imagen)
    }

    // Devuelve el número total de productos en la lista
    override fun getItemCount(): Int = productos.size
}
