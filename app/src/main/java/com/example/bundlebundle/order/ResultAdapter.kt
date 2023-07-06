import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bundlebundle.R


class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    private val resultData = mutableListOf<String>()
    private var listener: OnItemClickListener? = null

    fun setData(data: List<String>) {
        resultData.clear()
        resultData.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_addressresult, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val resultItem = resultData[position]
        holder.bind(resultItem)

        holder.itemView.setOnClickListener {
            val text = resultData[position]
            listener?.onItemClick(text)
        }
    }

    override fun getItemCount(): Int {
        return resultData.size
    }

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val resultTextView: TextView = itemView.findViewById(R.id.resultTextView)

        fun bind(resultItem: String) {
            resultTextView.text = resultItem

            itemView.setOnClickListener {
                val text = resultData[adapterPosition]
                listener?.onItemClick(text)
                Log.d("apiTestAddress", "Item clicked: $text")
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(text: String)
    }
}
