package com.example.appproyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.adaptadores.PlatoFuerteAdapter
import com.example.appproyectofinal.model.PlatosFuertes

class PlatofuerteActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_platofuerte)

        val regresar = findViewById<ImageView>(R.id.icono_regresar)
        regresar.setOnClickListener {
            val intent = Intent(this, CategoriaActivity3::class.java)
            startActivity(intent)
            finish()
        }

        val listaPlatos = listOf(
            PlatosFuertes("Ají de Gallina", "Ají cremoso con papa sancochada y arroz", 25.0, R.drawable.aji_de_gallina),
            PlatosFuertes("Seco de Res", "Guiso de res con frejoles y arroz", 28.0, R.drawable.seco_de_res),
            PlatosFuertes("Lomo Saltado", "Trozos de res salteados con papas y arroz", 30.0, R.drawable.lomo_saltado),
            PlatosFuertes("Arroz con Pollo", "Arroz verde con presa de pollo y salsa criolla", 24.0, R.drawable.tallarin_huancaina)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerPlatos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PlatoFuerteAdapter(listaPlatos)
    }
}
