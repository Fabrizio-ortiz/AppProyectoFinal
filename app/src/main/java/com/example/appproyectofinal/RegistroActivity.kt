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

        // Permitir un diseño a pantalla completa (Edge to Edge) para que la interfaz cubra toda la pantalla
        enableEdgeToEdge()

        // Establecer el diseño de la actividad
        setContentView(R.layout.activity_registro)

        // Configurar los márgenes de la vista principal para evitar que el contenido se superponga con las barras de sistema (como la barra de estado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los campos de entrada del formulario de registro
        nombresEditText = findViewById(R.id.nombres_edittext)
        apellidosEditText = findViewById(R.id.apellidos_edittext)
        correoEditText = findViewById(R.id.correo_edittext)
        celularEditText = findViewById(R.id.celular_edittext)
        contraseñaEditText = findViewById(R.id.contraseña_edittext)
        validarContraseñaEditText = findViewById(R.id.validarContraseña_edittext)
        registrarButton = findViewById(R.id.ingresar_button)

        // Obtener la instancia del DAO (Data Access Object) para interactuar con la base de datos
        userDao = AppDb.getDatabase(this).userDao()

        // Configurar el botón de registrar
        registrarButton.setOnClickListener {

            // Obtener los valores ingresados por el usuario
            val nombres = nombresEditText.text.toString().trim()
            val apellidos = apellidosEditText.text.toString().trim()
            val correo = correoEditText.text.toString().trim()
            val celular = celularEditText.text.toString().trim()
            val contraseña = contraseñaEditText.text.toString().trim()
            val confirmarContraseña = validarContraseñaEditText.text.toString().trim()

            // Validar que todos los campos estén completos
            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty()
                || celular.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar si las contraseñas coinciden
            if (contraseña != confirmarContraseña) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar si ya existe una cuenta con el correo ingresado
            lifecycleScope.launch {
                val existente = withContext(Dispatchers.IO) {
                    // Consultar la base de datos si ya existe un usuario con ese correo
                    userDao.getUsuarioPorEmail(correo)
                }

                // Si ya existe el correo, mostrar mensaje de error
                if (existente != null) {
                    Toast.makeText(this@RegistroActivity, "Ya existe una cuenta con este correo", Toast.LENGTH_SHORT).show()
                } else {
                    // Si el correo no existe, proceder a registrar al nuevo usuario
                    val nuevoUsuario = User(
                        email = correo,
                        password = contraseña,
                        nombres = nombres,
                        apellidos = apellidos,
                        celular = celular
                    )

                    // Insertar el nuevo usuario en la base de datos
                    withContext(Dispatchers.IO) {
                        userDao.insertarUsuario(nuevoUsuario)
                    }

                    // Notificar al usuario que el registro fue exitoso
                    Toast.makeText(this@RegistroActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish() // Finalizar esta actividad y regresar a la pantalla anterior (login)
                }
            }
        }

        // Configurar el botón de regresar, que permite volver a la pantalla anterior (Login)
        val botonRegresar = findViewById<ImageView>(R.id.icono_regresar)
        botonRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Regresar a la pantalla anterior
        }
    }

}