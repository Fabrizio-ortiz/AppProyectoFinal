package com.example.appproyectofinal

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.adaptadores.CarritoAdapter
import com.example.appproyectofinal.config.AppDb
import com.example.appproyectofinal.dao.Carrito
import com.example.appproyectofinal.model.PedidoEntity
import kotlinx.coroutines.launch

class CarritoActivity : AppCompatActivity() {
    private lateinit var totalCarrito: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val buttonRegresar = findViewById<ImageView>(R.id.icono_regresar)
        val recycler = findViewById<RecyclerView>(R.id.recyclerCarrito)
        totalCarrito = findViewById(R.id.totalCarrito)
        val btnRealizarPedido = findViewById<Button>(R.id.btnRealizarPedido)

        buttonRegresar.setOnClickListener {
            onBackPressed()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = CarritoAdapter(Carrito.items.toMutableList()) {
            actualizarTotal()
        }

        actualizarTotal()

        btnRealizarPedido.setOnClickListener {
            guardarPedidoEnRoom()
        }
    }

    private fun actualizarTotal() {
        val total = Carrito.items.sumOf { it.precio * it.cantidad }
        totalCarrito.text = "Total: S/ %.2f".format(total)
    }

    private fun guardarPedidoEnRoom() {
        val db = AppDb.getDatabase(this)

        lifecycleScope.launch {
            val dao = db.pedidoDao()
            for (item in Carrito.items) {
                val pedido = PedidoEntity(
                    nombrePlato = item.nombre,
                    cantidad = item.cantidad,
                    precioTotal = item.precio * item.cantidad
                )
                dao.insertarPedido(pedido)
            }

            Toast.makeText(this@CarritoActivity, "Pedido guardado con éxito", Toast.LENGTH_SHORT).show()
            Carrito.limpiar()
            finish()
        }
    }
}
