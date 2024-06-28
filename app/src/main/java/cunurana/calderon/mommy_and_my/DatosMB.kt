package cunurana.calderon.mommy_and_my

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log


class DatosMB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_datos_mb)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Agregando funciones a los botones
        findViewById<Button>(R.id.button1).setOnClickListener {
            Log.d("BUTTONS", "User tapped the button1")
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            Log.d("BUTTONS", "User tapped the button2")
        }

    }
}