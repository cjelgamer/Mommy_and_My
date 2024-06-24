package cunurana.calderon.mommy_and_my

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
//import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_sign_up = findViewById<Button>(R.id.btn_sign_up)
        //Toast.makeText(applicationContext,"Me presionaste......",Toast.LENGTH_SHORT).show()
        btn_sign_up.setOnClickListener {
            val intent = Intent(this, Crear_cuenta::class.java)
            startActivity(intent)
        }
    }

}


