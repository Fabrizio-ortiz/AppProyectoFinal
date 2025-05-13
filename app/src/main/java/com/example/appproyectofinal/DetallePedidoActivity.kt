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

    // Declaración de las vistas de la actividad
    private lateinit var txtNumeroPedido: TextView
    private lateinit var txtFecha: TextView
    private lateinit var txtMetodoEnvio: TextView
    private lateinit var txtMetodoPago: TextView
    private lateinit var txtTotal: TextView
    private lateinit var recyclerProductos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)

        // Inicializar las vistas de la actividad
        txtNumeroPedido = findViewById(R.id.txtNumeroPedido)
        txtFecha = findViewById(R.id.txtFecha)
        txtMetodoEnvio = findViewById(R.id.txtMetodoEnvio)
        txtMetodoPago = findViewById(R.id.txtMetodoPago)
        txtTotal = findViewById(R.id.txtTotal)
        recyclerProductos = findViewById(R.id.recyclerProductos)

        // Botón para regresar a la pantalla anterior
        val botonRegresar = findViewById<ImageView>(R.id.icono_regresar)

        // Obtener los datos del Intent que fueron enviados desde la actividad anterior
        val numeroPedido = intent.getStringExtra("numeroPedido") ?: "#000000" // Número del pedido
        val fecha = intent.getStringExtra("fecha") ?: "Sin fecha" // Fecha del pedido
        val metodoEnvio = intent.getStringExtra("metodoEnvio") ?: "Sin método" // Método de envío
        val metodoPago = intent.getStringExtra("metodoPago") ?: "Sin método" // Método de pago
        val total = intent.getDoubleExtra("total", 0.0) // Total del pedido

        // Establecer los valores obtenidos en los TextViews correspondientes
        txtNumeroPedido.text = "Número de Pedido: $numeroPedido"
        txtFecha.text = "Fecha de pedido: $fecha"
        txtMetodoEnvio.text = "Método de Envío: $metodoEnvio"
        txtMetodoPago.text = "Método de Pago: $metodoPago"
        txtTotal.text = "Total: S/ %.2f".format(total) // Formatear el total a dos decimales

        // Configurar el RecyclerView para mostrar los productos del pedido
        recyclerProductos.layoutManager = LinearLayoutManager(this)

        // Llamar al método que obtiene los productos del pedido de la base de datos
        obtenerProductosDePedido(numeroPedido)

        // Configurar el botón para regresar a la actividad anterior
        botonRegresar.setOnClickListener {
            onBackPressed()  // Este método cierra la actividad actual y regresa a la anterior
        }
    }

    // Método para obtener los productos relacionados con el pedido de la base de datos
    private fun obtenerProductosDePedido(numeroPedido: String) {
        val dao = AppDb.getDatabase(this@DetallePedidoActivity).productoDao()

        lifecycleScope.launch {
            // Obtener los productos del pedido con el número de pedido
            val productosEntity = dao.obtenerProductosPorNumeroPedido(numeroPedido)

            // Convertir los productos de la base de datos a objetos ItemCarrito para el RecyclerView
            val productos = productosEntity.map {
                ItemCarrito(
                    nombre = it.nombrePlato, // Nombre del plato
                    descripcion = "", // Aquí puedes ajustar la descripción si existe en tu modelo
                    precio = it.precio, // Precio del producto
                    imagen = it.imagenResId, // ID del recurso de la imagen (si tienes imágenes locales)
                    cantidad = it.cantidad // Cantidad de productos
                )
            }

            // Establecer el adaptador para el RecyclerView con la lista de productos
            recyclerProductos.adapter = DetallePedidoAdapter(productos)
        }
    }
}

