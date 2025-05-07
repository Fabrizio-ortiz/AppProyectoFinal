package com.example.appproyectofinal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.appproyectofinal.config.AppDb
import com.example.appproyectofinal.dao.UserDao
import com.example.appproyectofinal.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroActivity : AppCompatActivity() {

    private lateinit var nombresEditText: EditText
    private lateinit var apellidosEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var celularEditText: EditText
    private lateinit var contraseñaEditText: EditText
    private lateinit var validarContraseñaEditText: EditText
    private lateinit var registrarButton: Button
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los campos
        nombresEditText = findViewById(R.id.nombres_edittext)
        apellidosEditText = findViewById(R.id.apellidos_edittext)
        correoEditText = findViewById(R.id.correo_edittext)
        celularEditText = findViewById(R.id.celular_edittext)
        contraseñaEditText = findViewById(R.id.contraseña_edittext)
        validarContraseñaEditText = findViewById(R.id.validarContraseña_edittext)
        registrarButton = findViewById(R.id.ingresar_button)

        // Obtener instancia del DAO
        userDao = AppDb.getDatabase(this).userDao()


        registrarButton.setOnClickListener {

            val nombres = nombresEditText.text.toString().trim()
            val apellidos = apellidosEditText.text.toString().trim()
            val correo = correoEditText.text.toString().trim()
            val celular = celularEditText.text.toString().trim()
            val contraseña = contraseñaEditText.text.toString().trim()
            val confirmarContraseña = validarContraseñaEditText.text.toString().trim()

            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty()
                || celular.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contraseña != confirmarContraseña) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar si ya existe ese correo
            lifecycleScope.launch {
                val existente = withContext(Dispatchers.IO) {
                    userDao.getUsuarioPorEmail(correo)
                }

                if (existente != null) {
                    Toast.makeText(this@RegistroActivity, "Ya existe una cuenta con este correo", Toast.LENGTH_SHORT).show()
                } else {
                    // Insertar nuevo usuario
                    val nuevoUsuario = User(
                        email = correo,
                        password = contraseña,
                        nombres = nombres,
                        apellidos = apellidos,
                        celular = celular
                    )

                    withContext(Dispatchers.IO) {
                        userDao.insertarUsuario(nuevoUsuario)
                    }

                    Toast.makeText(this@RegistroActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish() // Volver a la pantalla anterior o Login
                }
            }
        }


        val botonRegresar = findViewById<ImageView>(R.id.icono_regresar)

        botonRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Para regresar a la pantalla anterior
        }
    }
}