package com.example.appproyectofinal

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
import com.example.appproyectofinal.dao.Carrito
import com.example.appproyectofinal.model.PedidoEntity
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

    // Lista de tiendas con sus direcciones
    private val tiendas = listOf(
        "Tienda Los Olivos - Dirección: Av. Siempre Viva 123",
        "Tienda Comas - Dirección: Calle Ficticia 456",
        "Tienda Callao - Dirección: Plaza Principal 789"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

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

        btnRealizarPedido.setOnClickListener {
            guardarPedidoEnRoom()
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

    private fun guardarPedidoEnRoom() {
        val db = AppDb.getDatabase(this)
        val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        lifecycleScope.launch {
            val dao = db.pedidoDao()
            for (item in Carrito.items) {
                val pedido = PedidoEntity(
                    nombrePlato = item.nombre,
                    cantidad = item.cantidad,
                    precioTotal = item.precio * item.cantidad,
                    fecha = fecha,
                    delivery = costoEnvio
                )
                dao.insertarPedido(pedido)
            }

            Toast.makeText(this@CarritoActivity, "Pedido guardado con éxito", Toast.LENGTH_SHORT).show()
            Carrito.limpiar()
            finish()
        }
    }
}
