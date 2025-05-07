package com.example.appproyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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

        val button_postres = findViewById<ImageView>(R.id.custom_button_postres)
        val button_platosFuertes = findViewById<ImageView>(R.id.custom_button_platosfuertes)

        button_postres.setOnClickListener{
            val intent = Intent(this, PostresActivity::class.java)
            startActivity(intent)
        }

        button_platosFuertes.setOnClickListener{
            val intent = Intent(this, PlatofuerteActivity3::class.java)
            startActivity(intent)
        }

    }
}