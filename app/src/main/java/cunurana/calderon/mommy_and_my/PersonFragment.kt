package cunurana.calderon.mommy_and_my

import DatabaseHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class PersonFragment : Fragment(R.layout.fragment_person) {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person, container, false)
        dbHelper = DatabaseHelper(requireContext())

        // Obtener el identificador del usuario de alguna manera
        val userId = 1L // Este valor debe ser dinámico, ajusta según tu lógica

        // Obtener datos del usuario
        val userData = dbHelper.getUserData(userId)

        // Rellenar las vistas con los datos del usuario
        val nameTextView: TextView = view.findViewById(R.id.textViewName)
        val ageTextView: TextView = view.findViewById(R.id.textViewAge)
        val maritalStatusTextView: TextView = view.findViewById(R.id.textViewMaritalStatus)

        Log.d("PersonFragment", "Fetched user data: $userData")

        userData?.let {
            nameTextView.text = it.name
            ageTextView.text = it.age.toString()
            maritalStatusTextView.text = it.maritalStatus
        } ?: run {
            Log.d("PersonFragment", "No user data found for user ID: $userId")
        }

        return view
    }
}
