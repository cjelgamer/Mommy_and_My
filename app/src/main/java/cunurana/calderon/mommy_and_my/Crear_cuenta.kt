package cunurana.calderon.mommy_and_my

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Crear_cuenta : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        // Verifica que el id rectangle_register exista en el layout activity_crear_cuenta
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rectangle_register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_login = findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener {
            val intent = Intent(this, iniciar_sesion::class.java)
            startActivity(intent)
        }

        val btn_ingresar = findViewById<Button>(R.id.btn_ingresar)
        btn_ingresar.setOnClickListener {
            val intent = Intent(this, datos_mb_1::class.java)
            startActivity(intent)
        }

    }
}
