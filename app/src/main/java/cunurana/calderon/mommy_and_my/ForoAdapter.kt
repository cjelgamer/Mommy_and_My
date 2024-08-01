package cunurana.calderon.mommy_and_my

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ForoAdapter : RecyclerView.Adapter<ForoAdapter.ForoViewHolder>() {

    private var forums = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_foro, parent, false)
        return ForoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForoViewHolder, position: Int) {
        holder.bind(forums[position])
    }

    override fun getItemCount(): Int {
        return forums.size
    }

    fun setForums(forums: List<String>) {
        this.forums = forums
        notifyDataSetChanged()
    }

    class ForoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foroTextView: TextView = itemView.findViewById(R.id.foroTextView)

        fun bind(forumText: String) {
            foroTextView.text = forumText
        }
    }
}
