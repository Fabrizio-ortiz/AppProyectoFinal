package com.example.appproyectofinal.viewmodel

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.R
import com.example.appproyectofinal.adaptadores.PlatoFuerteAdapter
import com.example.appproyectofinal.model.PlatosFuertes

class PlatoFuerteMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_platofuerte_main)


        // Lista manual de platos
        val listaPlatos = listOf(
            PlatosFuertes(1,"LOMO SALTADO", "Salteado con papas y arroz", 32.0, R.drawable.lomo_saltado),
            PlatosFuertes(2,"AJÍ DE GALLINA", "Crema de ají amarillo con pollo y arroz", 25.0, R.drawable.aji_de_gallina),
            PlatosFuertes(3,"SECO DE RES", "Con frejoles y arroz", 30.0, R.drawable.seco_de_res),
            PlatosFuertes(4,"CEVICHE", "Jugo de limón con pescado", 25.0, R.drawable.ceviche),
            PlatosFuertes(5,"TALLARÍN HUANCAINA C/LOMO", "", 49.0, R.drawable.tallarin_huancaina)
        )

        // Configurar RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerPlatos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PlatoFuerteAdapter(listaPlatos)
    }
}