package com.example.appproyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlatofuerteActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_platofuerte)


        val regresar_platosFuertes = findViewById<ImageView>(R.id.icono_regresar)

        regresar_platosFuertes.setOnClickListener{
            val intent = Intent(this, CategoriaActivity3::class.java)
            startActivity(intent)
        }
    }
}