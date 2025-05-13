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
    private lateinit var metodoPago: TextView
    private val deliveryFijo = 5.00 // Costo fijo por delivery
    private var costoEnvio = 0.00 // Inicializa el costo del envío

    private lateinit var correoUsuario: String // Correo del usuario que viene desde el Intent

    // Lista de tiendas para el método de recogida
    private val tiendas = listOf(
        "Tienda Los Olivos - Dirección: Av. Siempre Viva 123",
        "Tienda Comas - Dirección: Calle Ficticia 456",
        "Tienda Callao - Dirección: Plaza Principal 789"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        // Obtener el correo del usuario desde el Intent
        correoUsuario = intent.getStringExtra("usuarioCorreo") ?: ""

        // Referencias a las vistas
        val buttonRegresar = findViewById<ImageView>(R.id.icono_regresar)
        val recycler = findViewById<RecyclerView>(R.id.recyclerCarrito)
        totalCarrito = findViewById(R.id.totalCarrito)
        fechaPedido = findViewById(R.id.fechaPedido)
        costoDelivery = findViewById(R.id.costoDelivery)
        metodoEnvio = findViewById(R.id.txtMetodoEnvio)
        metodoPago = findViewById(R.id.txtMetodoPago)
        val btnRealizarPedido = findViewById<Button>(R.id.btnRealizarPedido)
        val btnSeleccionarMetodo = findViewById<Button>(R.id.btnSeleccionarMetodo)
        val btnSeleccionarMetodoPago = findViewById<Button>(R.id.btnSeleccionarMetodoPago)

        // Regresar a la actividad anterior al hacer clic en el ícono de regresar
        buttonRegresar.setOnClickListener {
            onBackPressed()
        }

        // Configurar RecyclerView para mostrar los ítems del carrito
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = CarritoAdapter(Carrito.items.toMutableList()) {
            actualizarTotal() // Actualizar el total del carrito cuando haya un cambio
        }
        mostrarFecha() // Mostrar la fecha de hoy
        actualizarTotal() // Mostrar el total inicial

        // Seleccionar el método de envío
        btnSeleccionarMetodo.setOnClickListener {
            mostrarOpcionesEnvio()
        }

        // Seleccionar el método de pago
        btnSeleccionarMetodoPago.setOnClickListener {
            mostrarOpcionesPago()
        }

        // Realizar el pedido al hacer clic en "Realizar Pedido"
        btnRealizarPedido.setOnClickListener {
            val metodoEnvioTexto = metodoEnvio.text.toString()
            val metodoPagoTexto = metodoPago.text.toString()

            // Validación para asegurarse de que se seleccionaron el envío y el pago
            if (metodoEnvioTexto == "Método de Envío: No seleccionado") {
                Toast.makeText(this, "Selecciona un método de envío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (metodoPagoTexto == "Método de Pago: No seleccionado") {
                Toast.makeText(this, "Selecciona un método de pago", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Generar número de pedido y guardarlo
            generarNumeroPedido(correoUsuario)
        }
    }

    private fun generarNumeroPedido(correo: String) {
        val db = AppDb.getDatabase(this)

        lifecycleScope.launch {
            var numeroPedido = 1
            val dao = db.pedidoDao()
            val ultimoPedido = dao.obtenerUltimoPedido() // Obtener el último pedido en la base de datos
            if (ultimoPedido != null) {
                numeroPedido = ultimoPedido.id + 1 // Incrementar el número de pedido
            }

            // Formatear el número de pedido
            val numeroPedidoFormateado = "#%06d".format(numeroPedido)

            // Guardar el pedido en la base de datos
            guardarPedidoEnRoom(numeroPedidoFormateado, correo)
        }
    }

    private fun mostrarFecha() {
        // Obtener y mostrar la fecha actual
        val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        fechaPedido.text = "Fecha: $fechaActual"
    }

    private fun mostrarOpcionesEnvio() {
        // Opciones para el método de envío (delivery o recoger en tienda)
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
                    mostrarTiendas() // Si elige recoger en tienda, mostrar opciones de tiendas
                }
            }
            actualizarTotal() // Actualizar el total del carrito
        }
        builder.show()
    }

    private fun mostrarOpcionesPago() {
        // Opciones para el método de pago (efectivo, Yape, Plin)
        val opcionesPago = arrayOf("Pago en efectivo", "Pago con Yape", "Pago con Plin")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona el método de pago")
        builder.setItems(opcionesPago) { dialog, which ->
            when (which) {
                0 -> {
                    metodoPago.text = "Pago en efectivo"
                }
                1 -> {
                    metodoPago.text = "Pago con Yape"
                }
                2 -> {
                    metodoPago.text = "Pago con Plin"
                }
            }
        }
        builder.show()
    }

    // Mostrar las tiendas disponibles si se selecciona "Recoger en tienda"
    private fun mostrarTiendas() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona tu tienda de recogida")
        builder.setItems(tiendas.toTypedArray()) { dialog, which ->
            metodoEnvio.text = "Método de Envío: Recoger en tienda - ${tiendas[which]}"
        }
        builder.show()
    }

    private fun actualizarTotal() {
        // Sumar el total de los productos en el carrito y el costo de envío
        val total = Carrito.items.sumOf { it.precio * it.cantidad }
        totalCarrito.text = "Total: S/ %.2f".format(total + costoEnvio)
        costoDelivery.text = "Delivery: S/ %.2f".format(costoEnvio)
    }

    private fun guardarPedidoEnRoom(numeroPedido: String, email: String) {
        val db = AppDb.getDatabase(this)
        val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        // Determinar el método de envío seleccionado
        val metodoEnvioSeleccionado = if (costoEnvio == deliveryFijo) {
            "Delivery"
        } else {
            val tiendaSeleccionada = metodoEnvio.text.toString().split("-")[1].trim()
            "Recoger en tienda ($tiendaSeleccionada)"
        }

        // Obtener el método de pago seleccionado
        val metodoPagoSeleccionado = metodoPago.text.toString().trim()

        lifecycleScope.launch {
            val dao = db.pedidoDao()

            // Insertar el pedido en la base de datos
            val pedido = PedidoEntity(
                nombrePlato = "Pedido $numeroPedido",
                cantidad = Carrito.items.size,
                precioTotal = Carrito.items.sumOf { it.precio * it.cantidad } + costoEnvio,
                fecha = fecha,
                delivery = costoEnvio,
                metodoEnvio = metodoEnvioSeleccionado,
                metodoPago = metodoPagoSeleccionado,
                numeroPedido = numeroPedido,
                email = email
            )
            dao.insertarPedido(pedido)

            // Insertar los productos en la tabla "productos_pedido"
            val productoDao = db.productoDao()
            for (item in Carrito.items) {
                val productoPedido = ProductoPedidoEntity(
                    pedidoId = pedido.id,
                    nombrePlato = item.nombre,
                    cantidad = item.cantidad,
                    precio = item.precio,
                    numeroPedido = numeroPedido,
                    imagenResId = item.imagen
                )
                productoDao.insertarProducto(productoPedido)
            }

            // Navegar a la pantalla de detalles del pedido
            val intent = Intent(this@CarritoActivity, DetallePedidoActivity::class.java).apply {
                putExtra("numeroPedido", numeroPedido)
                putExtra("fecha", fecha)
                putExtra("metodoEnvio", metodoEnvioSeleccionado)
                putExtra("metodoPago", metodoPagoSeleccionado)
                putExtra("total", Carrito.items.sumOf { it.precio * it.cantidad } + costoEnvio)
            }
            startActivity(intent)

            // Mensaje de éxito y limpiar el carrito
            Toast.makeText(this@CarritoActivity, "Pedido guardado con éxito", Toast.LENGTH_SHORT).show()
            Carrito.limpiar() // Limpiar el carrito después de realizar el pedido
            finish() // Cerrar la actividad actual
        }
    }
}

