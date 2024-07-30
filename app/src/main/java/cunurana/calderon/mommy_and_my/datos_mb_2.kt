package cunurana.calderon.mommy_and_my

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class datos_mb_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datos_mb2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Encuentra los EditText usando los IDs definidos en el XML
        val nombreEditText = findViewById<EditText>(R.id.btn_msj_name)
        val edadEditText = findViewById<EditText>(R.id.btn_msj_edad)
        val tieneHijosEditText = findViewById<EditText>(R.id.btn_msj_hijos)
        val estadoCivilEditText = findViewById<EditText>(R.id.editText2)

        val btnContinueDatos = findViewById<Button>(R.id.btn_continue_datos)
        btnContinueDatos.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val edad = edadEditText.text.toString().toIntOrNull()
            val tieneHijos = tieneHijosEditText.text.toString()
            val estadoCivil = estadoCivilEditText.text.toString()

            // Verificar si todos los campos están completos
            if (nombre.isEmpty() || edad == null || tieneHijos.isEmpty() || estadoCivil.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear instancia de DatabaseHelper y guardar los datos
            val dbHelper = DatabaseHelper(this)
            val userDataId = dbHelper.insertUserData(nombre, edad, tieneHijos, estadoCivil)
            dbHelper.close()

            if (userDataId > -1) {
                Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
                // Iniciar la siguiente actividad
                val intent = Intent(this, Menuprin::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
