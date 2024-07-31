package cunurana.calderon.mommy_and_my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast


class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bt1: ImageButton = view.findViewById(R.id.item01)
        bt1.setOnClickListener {
            Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
            val nextFragment = Calendario()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.contendor_fragmentos, nextFragment)
                .addToBackStack(null)
                .commit()
        }
        val bt2: ImageButton = view.findViewById(R.id.item02)
        bt2.setOnClickListener {
            Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
            val nextFragment = PrevencionFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.contendor_fragmentos, nextFragment)
                .addToBackStack(null)
                .commit()
        }
        val bt3: ImageButton = view.findViewById(R.id.item03)
        bt3.setOnClickListener {
            Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
            val nextFragment = MonitoreoBebeFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.contendor_fragmentos, nextFragment)
                .addToBackStack(null)
                .commit()
        }
        val bt4: ImageButton = view.findViewById(R.id.item04)
        bt4.setOnClickListener {
            Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
            val nextFragment = MamaInformadaFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.contendor_fragmentos, nextFragment)
                .addToBackStack(null)
                .commit()
        }
        val bt5: ImageButton = view.findViewById(R.id.item05)
        bt5.setOnClickListener {
            Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
            val nextFragment = ControlFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.contendor_fragmentos, nextFragment)
                .addToBackStack(null)
                .commit()
        }
        val bt6: ImageButton = view.findViewById(R.id.item06)
        bt6.setOnClickListener {
            Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
            val nextFragment = ForoFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.contendor_fragmentos, nextFragment)
                .addToBackStack(null)
                .commit()

        }
        val btf: ImageButton = view.findViewById(R.id.item07)
        btf.setOnClickListener {
            Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
            val nextFragment = PropioFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.contendor_fragmentos, nextFragment)
                .addToBackStack(null)
                .commit()
        }
    }

}