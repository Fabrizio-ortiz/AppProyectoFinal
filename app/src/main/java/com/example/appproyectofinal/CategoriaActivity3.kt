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

        // Permitir un diseño a pantalla completa (Edge to Edge) para que la interfaz cubra toda la pantalla
        enableEdgeToEdge()

        // Establecer el diseño de la actividad
        setContentView(R.layout.activity_categoria)

        // Configurar los márgenes de la vista principal para evitar que el contenido se superponga con las barras de sistema (como la barra de estado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener el correo del usuario que se pasó en el Intent anterior
        val correoUsuario = intent.getStringExtra("usuarioCorreo") ?: ""

        // Referencias a los elementos de la interfaz de usuario (botones e iconos)
        val iconoHistorial = findViewById<ImageView>(R.id.icono_historial)

        val button_entradas = findViewById<ImageView>(R.id.custom_button_entrada)
        val button_bebidas = findViewById<ImageView>(R.id.custom_button_bebidas)
        val button_postres = findViewById<ImageView>(R.id.custom_button_postres)
        val button_platosFuertes = findViewById<ImageView>(R.id.custom_button_platosfuertes)
        val button_regresar_categoria = findViewById<ImageView>(R.id.icono_regresar)

        // Configurar el listener para el botón "Entradas", que redirige a la actividad de EntradasActivity
        button_entradas.setOnClickListener{
            val intent = Intent(this, EntradasActivity::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario) // Pasar el correo del usuario
            startActivity(intent) // Iniciar la actividad de entradas
        }

        // Configurar el listener para el botón "Bebidas", que redirige a la actividad de BebidasActivity
        button_bebidas.setOnClickListener {
            val intent = Intent(this, BebidasActivity::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario) // Pasar el correo del usuario
            startActivity(intent) // Iniciar la actividad de bebidas
        }

        // Configurar el listener para el botón "Postres", que redirige a la actividad de PostresActivity
        button_postres.setOnClickListener{
            val intent = Intent(this, PostresActivity::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario) // Pasar el correo del usuario
            startActivity(intent) // Iniciar la actividad de postres
        }

        // Configurar el listener para el botón "Platos Fuertes", que redirige a la actividad de PlatofuerteActivity3
        button_platosFuertes.setOnClickListener{
            val intent = Intent(this, PlatofuerteActivity3::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario) // Pasar el correo del usuario
            startActivity(intent) // Iniciar la actividad de platos fuertes
        }

        // Configurar el listener para el botón "Regresar", que redirige al login
        button_regresar_categoria.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // Iniciar la actividad del login
        }

        // Configurar el listener para el icono "Historial de Pedidos", que redirige a la actividad HistorialPedidosActivity
        iconoHistorial.setOnClickListener {
            val intent = Intent(this, HistorialPedidosActivity::class.java)
            intent.putExtra("usuarioCorreo", correoUsuario) // Pasar el correo del usuario
            startActivity(intent) // Iniciar la actividad de historial de pedidos
        }

    }
}

