package com.example.appproyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.adaptadores.EntradaAdapter
import com.example.appproyectofinal.model.Entrada

class EntradasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_entradas)

        val regresar_entradas = findViewById<ImageView>(R.id.icono_regresar)

        // Obtener el correo del usuario desde el Intent
        val correoUsuario = intent.getStringExtra("usuarioCorreo") ?: ""

        regresar_entradas.setOnClickListener{
            val intent = Intent(this, CategoriaActivity3::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario) // <-- Asegúrate de reenviarlo
            startActivity(intent)
            startActivity(intent)
        }

        val listaEntradas = listOf(
            Entrada("Papa a la huancaína", 5.0, R.drawable.papa_huancaina),
            Entrada("Ocopa arequipeña", 8.0, R.drawable.ocopa_arequipena),
            Entrada("Causa limeña", 10.0, R.drawable.causa_limena),
            Entrada("Tamales", 4.0, R.drawable.tamales),
            Entrada("Humitas", 6.0, R.drawable.humitas),
            Entrada("Leche de tigre", 15.0, R.drawable.leche_tigre),
            Entrada("Choclo con queso",5.0,R.drawable.choclo_con_queso),
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerEntradas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EntradaAdapter(listaEntradas)

        findViewById<Button>(R.id.btnVerCarrito).setOnClickListener {
            // Pasar el correo al carrito
            val intent = Intent(this, CarritoActivity::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario)  // Añadir el correo al Intent
            startActivity(intent)
        }
    }
}