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

class datos_mb_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datos_mb3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Encuentra el EditText y el botón usando los IDs definidos en el XML
        val fechaPartoEditText = findViewById<EditText>(R.id.btn_msj_agree)
        val btnContinueDatos = findViewById<Button>(R.id.btn_continue_datos)

        btnContinueDatos.setOnClickListener {
            val fechaParto = fechaPartoEditText.text.toString()

            // Verificar si el campo está completo
            if (fechaParto.isEmpty()) {
                Toast.makeText(this, "Por favor completa el campo de fecha", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear instancia de DatabaseHelper y guardar la fecha
            val dbHelper = DatabaseHelper(this)
            val birthDateId = dbHelper.insertBirthDate(fechaParto)
            dbHelper.close()

            if (birthDateId > -1) {
                Toast.makeText(this, "Fecha guardada exitosamente", Toast.LENGTH_SHORT).show()

                // Navegar a la siguiente actividad
                val intent = Intent(this, Menuprin::class.java) // Cambia a la actividad que desees
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al guardar la fecha", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
