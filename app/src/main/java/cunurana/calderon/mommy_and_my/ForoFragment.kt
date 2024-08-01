package cunurana.calderon.mommy_and_my

import DatabaseHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class ForoFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var foroEditText: EditText
    private lateinit var publishButton: MaterialButton
    private lateinit var foroRecyclerView: RecyclerView
    private lateinit var foroAdapter: ForoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el DatabaseHelper
        databaseHelper = DatabaseHelper(requireContext())

        // Encuentra las vistas
        foroEditText = view.findViewById(R.id.foroEditText)
        publishButton = view.findViewById(R.id.publishButton)
        foroRecyclerView = view.findViewById(R.id.foroRecyclerView)

        // Configura el RecyclerView
        foroRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        foroAdapter = ForoAdapter()
        foroRecyclerView.adapter = foroAdapter

        // Carga los foros existentes
        loadForums()

        // Configura el listener para el botón de publicar
        publishButton.setOnClickListener {
            val forumText = foroEditText.text.toString()
            if (forumText.isNotEmpty()) {
                // Inserta el texto del foro en la base de datos
                val result = databaseHelper.insertForum(forumText)
                if (result != -1L) {
                    Toast.makeText(requireContext(), "Foro publicado exitosamente", Toast.LENGTH_SHORT).show()
                    foroEditText.text.clear()
                    loadForums() // Carga los foros nuevamente para mostrar el nuevo
                } else {
                    Toast.makeText(requireContext(), "Error al publicar el foro", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "El texto del foro no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadForums() {
        val forums = databaseHelper.getAllForums()
        foroAdapter.setForums(forums)
    }
}
