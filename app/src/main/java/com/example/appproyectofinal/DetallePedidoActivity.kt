package com.example.appproyectofinal

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.adaptadores.DetallePedidoAdapter
import com.example.appproyectofinal.config.AppDb
import com.example.appproyectofinal.model.ItemCarrito
import kotlinx.coroutines.launch

class DetallePedidoActivity : AppCompatActivity() {

    private lateinit var txtNumeroPedido: TextView
    private lateinit var txtFecha: TextView
    private lateinit var txtMetodoEnvio: TextView
    private lateinit var txtMetodoPago: TextView
    private lateinit var txtTotal: TextView
    private lateinit var recyclerProductos: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)

        txtNumeroPedido = findViewById(R.id.txtNumeroPedido)
        txtFecha = findViewById(R.id.txtFecha)
        txtMetodoEnvio = findViewById(R.id.txtMetodoEnvio)
        txtMetodoPago = findViewById(R.id.txtMetodoPago)
        txtTotal = findViewById(R.id.txtTotal)
        recyclerProductos = findViewById(R.id.recyclerProductos)
        val botonRegresar = findViewById<ImageView>(R.id.icono_regresar)

        // Obtener el número de pedido y otros detalles
        val numeroPedido = intent.getStringExtra("numeroPedido") ?: "#000000"
        val fecha = intent.getStringExtra("fecha") ?: "Sin fecha"
        val metodoEnvio = intent.getStringExtra("metodoEnvio") ?: "Sin método"
        val metodoPago = intent.getStringExtra("metodoPago") ?: "Sin método"
        val total = intent.getDoubleExtra("total", 0.0)

        // Establecer los valores en la UI
        txtNumeroPedido.text = "Número de Pedido: $numeroPedido"
        txtFecha.text = "Fecha de pedido: $fecha"
        txtMetodoEnvio.text = "Método de Envío: $metodoEnvio"
        txtMetodoPago.text = "Método de Pago: $metodoPago"
        txtTotal.text = "Total: S/ %.2f".format(total)

        // Establecer el RecyclerView
        recyclerProductos.layoutManager = LinearLayoutManager(this)

        // Aquí deberías obtener los productos de este pedido, por ejemplo:
        obtenerProductosDePedido(numeroPedido)

        // Configurar el botón de regresar
        botonRegresar.setOnClickListener {

            onBackPressed()  // Este método cierra la actividad actual y regresa a la anterior
        }
    }

    private fun obtenerProductosDePedido(numeroPedido: String) {
        val db = AppDb.getDatabase(this)

        lifecycleScope.launch {
            val db = AppDb.getDatabase(this@DetallePedidoActivity)
            val dao = db.productoDao()
            val productosEntity = dao.obtenerProductosPorNumeroPedido(numeroPedido)

            val productos = productosEntity.map {
                ItemCarrito(
                    nombre = it.nombrePlato,
                    descripcion = "", // Puedes ajustar esto si hay campo descripción
                    precio = it.precio,
                    imagen = it.imagenResId, // Si tienes la imagen almacenada, ajústalo
                    cantidad = it.cantidad
                )
            }

            recyclerProductos.adapter = DetallePedidoAdapter(productos)
        }
    }
}


