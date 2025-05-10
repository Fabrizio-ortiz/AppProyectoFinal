package com.example.appproyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectofinal.adaptadores.PostreAdapter
import com.example.appproyectofinal.model.Postre

class PostresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_postres)

        val regresar_postres = findViewById<ImageView>(R.id.icono_regresar)

        regresar_postres.setOnClickListener{
            val intent = Intent(this, CategoriaActivity3::class.java)
            startActivity(intent)
        }

        val listaPostres = listOf(
            Postre("Mazamorra morada", 8.0, R.drawable.mazamorra_morada),
            Postre("Arroz con leche", 10.0, R.drawable.arroz_con_leche),
            Postre("Mazamorra morada con arroz con leche", 11.0, R.drawable.arroz_con_leche_mazamorra_morada),
            Postre("Suspiro a la limeña", 7.0, R.drawable.suspiro_limeno),
            Postre("Picaranos", 8.0, R.drawable.picarones),
            Postre("Torta 3 leches", 10.0, R.drawable.torta_3leches),
            Postre("Crema volteada",5.0,R.drawable.crema_volteada),
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerPostres)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PostreAdapter(listaPostres)
    }
}