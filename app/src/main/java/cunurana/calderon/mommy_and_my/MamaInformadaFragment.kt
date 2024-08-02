package cunurana.calderon.mommy_and_my

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


class MamaInformadaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mama_informada, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val includeView = view.findViewById<View>(R.id.bpregunta2)
        val item1View = includeView.findViewById<View>(R.id.item1)

        item1View.setOnClickListener {
            Toast.makeText(context, "Item clicado", Toast.LENGTH_SHORT).show()
            val url = "https://example.com" //
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}