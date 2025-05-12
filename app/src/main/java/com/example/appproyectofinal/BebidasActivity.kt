package com.example.appproyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.adaptadores.BebidaAdapter
import com.example.appproyectofinal.adaptadores.EntradaAdapter
import com.example.appproyectofinal.model.Bebida
import com.example.appproyectofinal.model.Entrada

class BebidasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bebidas)

        val regresar_entradas = findViewById<ImageView>(R.id.icono_regresar)

        // Obtener el correo del usuario desde el Intent
        val correoUsuario = intent.getStringExtra("usuarioCorreo") ?: ""

        regresar_entradas.setOnClickListener{
            val intent = Intent(this, CategoriaActivity3::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario) // <-- Asegúrate de reenviarlo
            startActivity(intent)
            startActivity(intent)
        }

        val listaBebidas = listOf(
            Bebida("Chicha morada","1 litro", 10.0, R.drawable.chicha_morada),
            Bebida("Maracuyá", "1 litro",10.0, R.drawable.maracuya),
            Bebida("Limonda", "1 litro",8.0, R.drawable.limonada),
            Bebida("Carambola", "1 litro",8.0, R.drawable.carambola),
            Bebida("Inka Kola", "1 litro y medio",6.0, R.drawable.inka_kola),
            Bebida("Coca Cola", "1 litro y medio",6.50, R.drawable.coca_cola),
            Bebida("Agua mineral","1 litro",5.0,R.drawable.agura_mineral),
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerBebidas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BebidaAdapter(listaBebidas)

        findViewById<Button>(R.id.btnVerCarrito).setOnClickListener {
            // Pasar el correo al carrito
            val intent = Intent(this, CarritoActivity::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario)  // Añadir el correo al Intent
            startActivity(intent)
        }
    }
}