package com.example.appproyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.adaptadores.CarritoAdapter
import com.example.appproyectofinal.config.AppDb
import com.example.appproyectofinal.model.PedidoEntity
import com.example.appproyectofinal.model.ProductoPedidoEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CarritoActivity : AppCompatActivity() {
    private lateinit var totalCarrito: TextView
    private lateinit var fechaPedido: TextView
    private lateinit var costoDelivery: TextView
    private lateinit var metodoEnvio: TextView
    private val deliveryFijo = 5.00
    private var costoEnvio = 0.00

    private lateinit var correoUsuario: String // Mover esta declaración fuera del intent

    // Lista de tiendas con sus direcciones
    private val tiendas = listOf(
        "Tienda Los Olivos - Dirección: Av. Siempre Viva 123",
        "Tienda Comas - Dirección: Calle Ficticia 456",
        "Tienda Callao - Dirección: Plaza Principal 789"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        // Obtener el correo del usuario de los extras del Intent
        correoUsuario = intent.getStringExtra("usuarioCorreo") ?: ""

        val buttonRegresar = findViewById<ImageView>(R.id.icono_regresar)
        val recycler = findViewById<RecyclerView>(R.id.recyclerCarrito)
        totalCarrito = findViewById(R.id.totalCarrito)
        fechaPedido = findViewById(R.id.fechaPedido)
        costoDelivery = findViewById(R.id.costoDelivery)
        metodoEnvio = findViewById(R.id.txtMetodoEnvio)
        val btnRealizarPedido = findViewById<Button>(R.id.btnRealizarPedido)
        val btnSeleccionarMetodo = findViewById<Button>(R.id.btnSeleccionarMetodo)

        buttonRegresar.setOnClickListener {
            onBackPressed()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = CarritoAdapter(Carrito.items.toMutableList()) {
            actualizarTotal()
        }
        mostrarFecha()
        actualizarTotal()

        btnSeleccionarMetodo.setOnClickListener {
            mostrarOpcionesEnvio()
        }

        // Llamar a la función de generar número de pedido cuando el usuario hace clic en "Realizar Pedido"
        btnRealizarPedido.setOnClickListener {
            generarNumeroPedido(correoUsuario) // Pasar el correo al generar el número de pedido
        }
    }

    private fun generarNumeroPedido(correo: String) {
        val db = AppDb.getDatabase(this)

        lifecycleScope.launch {
            var numeroPedido = 1

            val dao = db.pedidoDao()
            val ultimoPedido = dao.obtenerUltimoPedido() // Obtener el último pedido
            if (ultimoPedido != null) {
                numeroPedido = ultimoPedido.id + 1 // El número del nuevo pedido será 1 más que el último
            }

            // Formatear el número con ceros a la izquierda (ejemplo #000232)
            val numeroPedidoFormateado = "#%06d".format(numeroPedido)

            // Llamar a la función para guardar el pedido, pasando el número generado y el correo
            guardarPedidoEnRoom(numeroPedidoFormateado, correo)
        }
    }

    private fun mostrarFecha() {
        val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        fechaPedido.text = "Fecha: $fechaActual"
    }

    private fun mostrarOpcionesEnvio() {
        val opciones = arrayOf("Delivery (S/ 5.00)", "Recoger en tienda (Sin costo)")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona el método de envío")
        builder.setItems(opciones) { dialog, which ->
            when (which) {
                0 -> {
                    metodoEnvio.text = "Método de Envío: Delivery (S/ 5.00)"
                    costoEnvio = deliveryFijo
                }
                1 -> {
                    metodoEnvio.text = "Método de Envío: Recoger en tienda"
                    costoEnvio = 0.00
                    mostrarTiendas() // Mostrar las tiendas al seleccionar "Recoger en tienda"
                }
            }
            actualizarTotal() // Actualizar el total después de seleccionar el método
        }
        builder.show()
    }

    // Mostrar las tiendas disponibles para recoger en tienda
    private fun mostrarTiendas() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona tu tienda de recogida")
        builder.setItems(tiendas.toTypedArray()) { dialog, which ->
            // Mostrar la tienda seleccionada
            metodoEnvio.text = "Método de Envío: Recoger en tienda - ${tiendas[which]}"
        }
        builder.show()
    }

    private fun actualizarTotal() {
        val total = Carrito.items.sumOf { it.precio * it.cantidad }
        // Sumar el costo de delivery o no
        totalCarrito.text = "Total: S/ %.2f".format(total + costoEnvio)
        costoDelivery.text = "Delivery: S/ %.2f".format(costoEnvio)
    }

    private fun guardarPedidoEnRoom(numeroPedido: String, email: String) {
        val db = AppDb.getDatabase(this)
        val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        // Obtener el nombre del método de envío
        val metodoEnvioSeleccionado = if (costoEnvio == deliveryFijo) {
            "Delivery"
        } else {
            // Obtener el nombre de la tienda seleccionada si se seleccionó "Recoger en tienda"
            val tiendaSeleccionada = metodoEnvio.text.toString().split("-")[1].trim()
            "Recoger en tienda ($tiendaSeleccionada)"
        }

        lifecycleScope.launch {
            val dao = db.pedidoDao()

            // Insertar el pedido en la tabla "pedidos"
            val pedido = PedidoEntity(
                nombrePlato = "Pedido $numeroPedido", // Puedes incluir un nombre genérico para el pedido
                cantidad = Carrito.items.size,  // Número de ítems en el carrito
                precioTotal = Carrito.items.sumOf { it.precio * it.cantidad } + costoEnvio,
                fecha = fecha,
                delivery = costoEnvio,
                metodoEnvio = metodoEnvioSeleccionado,
                numeroPedido = numeroPedido,
                email = email // Correo del usuario
            )
            dao.insertarPedido(pedido)

            // Insertar los productos en la tabla "productos_pedido"
            val productoDao = db.productoDao() // Asegúrate de tener un DAO para ProductoPedidoEntity
            for (item in Carrito.items) {
                val productoPedido = ProductoPedidoEntity(
                    pedidoId = pedido.id, // Esto es importante para la relación entre PedidoEntity y ProductoPedidoEntity
                    nombrePlato = item.nombre,
                    cantidad = item.cantidad,
                    precio = item.precio,
                    numeroPedido = numeroPedido,
                    imagenResId = item.imagen
                )
                productoDao.insertarProducto(productoPedido) // Inserta el producto
            }

            // Navegar a la actividad de detalle del pedido
            val intent = Intent(this@CarritoActivity, DetallePedidoActivity::class.java).apply {
                putExtra("numeroPedido", numeroPedido)
                putExtra("fecha", fecha)
                putExtra("metodoEnvio", metodoEnvioSeleccionado)
                putExtra("total", Carrito.items.sumOf { it.precio * it.cantidad } + costoEnvio)
            }
            startActivity(intent)

            Toast.makeText(this@CarritoActivity, "Pedido guardado con éxito", Toast.LENGTH_SHORT).show()
            Carrito.limpiar()
            finish()
        }
    }

}
