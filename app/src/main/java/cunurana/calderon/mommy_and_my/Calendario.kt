package cunurana.calderon.mommy_and_my

import DatabaseHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Calendario.newInstance] factory method to
 * create an instance of this fragment.
 */
class Calendario : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var textFechaSeleccionada: TextView
    private lateinit var notasDia: EditText
    private lateinit var btnGuardarRecordatorio: Button
    private lateinit var btnEliminarRecordatorio: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        calendarView = view.findViewById(R.id.calendary_view) // Asegúrate de que este ID coincida con el del XML
        textFechaSeleccionada = view.findViewById(R.id.textFechaSeleccionada)
        notasDia = view.findViewById(R.id.notas_dia)
        btnGuardarRecordatorio = view.findViewById(R.id.btn_guardar_recordatorio)
        btnEliminarRecordatorio = view.findViewById(R.id.btn_eliminar_recordatorio)

        // Initialize DatabaseHelper
        dbHelper = DatabaseHelper(requireContext())

        // Set listener for date changes in CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$year-${month + 1}-$dayOfMonth"
            textFechaSeleccionada.text = date
            loadReminder(date)
        }

        // Set OnClickListener for save button
        btnGuardarRecordatorio.setOnClickListener {
            val date = textFechaSeleccionada.text.toString()
            val note = notasDia.text.toString()
            if (date.isNotEmpty() && note.isNotEmpty()) {
                if (note.length <= 50) { // Optionally add length check
                    dbHelper.insertReminder(date, note)
                    Toast.makeText(requireContext(), "Recordatorio guardado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "La nota no puede exceder 50 caracteres", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Debe seleccionar una fecha y agregar una nota", Toast.LENGTH_SHORT).show()
            }
        }

        // Set OnClickListener for delete button
        btnEliminarRecordatorio.setOnClickListener {
            val date = textFechaSeleccionada.text.toString()
            if (date.isNotEmpty()) {
                dbHelper.deleteReminder(date)
                notasDia.text.clear()
                Toast.makeText(requireContext(), "Recordatorio eliminado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Debe seleccionar una fecha para eliminar el recordatorio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Load reminder note from database
    private fun loadReminder(date: String) {
        val note = dbHelper.getReminder(date)
        notasDia.setText(note ?: "")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Calendario.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Calendario().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
