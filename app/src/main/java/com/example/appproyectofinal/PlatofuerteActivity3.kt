package com.example.appproyectofinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
            PlatosFuertes("Arroz con Pollo", "Arroz verde con presa de pollo y salsa criolla", 24.0, R.drawable.arroz_con_pollo),
            PlatosFuertes("Tall. a la huancaina c/ lomo saltado ", "Tallarines en salsa huancaína con jugoso lomo salteado al estilo criollo", 31.0, R.drawable.tallarin_huancaina),
            PlatosFuertes("Ceviche", "Jugo de limón con pescado", 28.0, R.drawable.ceviche),
            PlatosFuertes("Arroz Chaufa","Arroz frito con huevo, pollo y un toque oriental criollo",20.0,R.drawable.arroz_chaufa),
            PlatosFuertes("Carapulcra con sopa seca","Papa seca guisada con fideos al estilo chinchano",30.0,R.drawable.carapulcra_con_sopa_seca),
            PlatosFuertes("Cau cau de mondongo","Guiso de mondongo con papa y hierbabuena",22.0,R.drawable.cau_cau_de_mondongo),
            PlatosFuertes("Anticuchos","Corazón de res a la parrilla con papas y ají criollo",25.0,R.drawable.anticuchos),
            PlatosFuertes("Chanfainita","Pulmón de res guisado con papa y ají panca",25.0,R.drawable.chanfauinita),
            PlatosFuertes("Tacu tacu con lomo","Tortilla de arroz y frejoles con lomo salteado",28.0,R.drawable.tacu_tacu_con_lomo_saltado)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerPlatos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PlatoFuerteAdapter(listaPlatos)

        findViewById<Button>(R.id.btnVerCarrito).setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }
    }
}
