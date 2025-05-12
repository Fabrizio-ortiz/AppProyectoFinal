package com.example.appproyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.adaptadores.HistorialPedidoAdapter
import com.example.appproyectofinal.config.AppDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistorialPedidosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_pedidos)


        recyclerView = findViewById(R.id.recyclerHistorialPedidos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val botonRegresar = findViewById<ImageView>(R.id.icono_regresar)

        // Obtener el correo del usuario desde el Intent
        val emailUsuario = intent.getStringExtra("usuarioCorreo") ?: ""

        // Verificar si el email está vacío
        if (emailUsuario.isNotEmpty()) {
            obtenerPedidosDeUsuario(emailUsuario)
        } else {
            Toast.makeText(this, "Correo no disponible", Toast.LENGTH_SHORT).show()
        }

        // Configurar el botón de regresar
        botonRegresar.setOnClickListener {
            onBackPressed()  // Este método cierra la actividad actual y regresa a la anterior
        }
    }

    private fun obtenerPedidosDeUsuario(email: String) {
        lifecycleScope.launch {
            try {
                val db = AppDb.getDatabase(applicationContext)
                val pedidoDao = db.pedidoDao()

                // Obtener los pedidos del usuario desde la base de datos
                val pedidos = pedidoDao.obtenerPedidosPorUsuario(email)

                // Establecer el adaptador del RecyclerView con los pedidos recuperados
                recyclerView.adapter = HistorialPedidoAdapter(pedidos) { pedido ->
                    // Al hacer clic en un pedido, ir a la pantalla de detalle del pedido
                    val intent = Intent(this@HistorialPedidosActivity, DetallePedidoActivity::class.java).apply {
                        putExtra("numeroPedido", pedido.numeroPedido)
                        putExtra("fecha", pedido.fecha)
                        putExtra("metodoEnvio", pedido.metodoEnvio)
                        putExtra("metodoPago", pedido.metodoPago)
                        putExtra("total", pedido.precioTotal)  // Usamos el precioTotal como el total
                    }
                    startActivity(intent)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@HistorialPedidosActivity, "Error al cargar los pedidos: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


