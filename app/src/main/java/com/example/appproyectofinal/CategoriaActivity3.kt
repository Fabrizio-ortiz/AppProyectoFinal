package com.example.appproyectofinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CategoriaActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_categoria)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val correoUsuario = intent.getStringExtra("usuarioCorreo") ?: ""

        val iconoHistorial = findViewById<ImageView>(R.id.icono_historial)

        val button_entradas = findViewById<ImageView>(R.id.custom_button_entrada)
        val button_bebidas = findViewById<ImageView>(R.id.custom_button_bebidas)
        val button_postres = findViewById<ImageView>(R.id.custom_button_postres)
        val button_platosFuertes = findViewById<ImageView>(R.id.custom_button_platosfuertes)
        val button_regresar_categoria = findViewById<ImageView>(R.id.icono_regresar)

        button_entradas.setOnClickListener{
            val intent = Intent(this, EntradasActivity::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario)
            startActivity(intent)
        }

        button_bebidas.setOnClickListener {
            val intent = Intent(this, BebidasActivity::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario)
            startActivity(intent)
        }

        button_postres.setOnClickListener{
            val intent = Intent(this, PostresActivity::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario)
            startActivity(intent)
        }

        button_platosFuertes.setOnClickListener{
            val intent = Intent(this, PlatofuerteActivity3::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario)
            startActivity(intent)
        }

        button_regresar_categoria.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        iconoHistorial.setOnClickListener {

            val intent = Intent(this, HistorialPedidosActivity::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario)
            startActivity(intent)
        }

    }
}
