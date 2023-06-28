import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.example.bundlebundle.placeholder.PlaceholderContent.PlaceholderItem
import com.example.bundlebundle.databinding.FragmentProductGridBinding

class ProductItemRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<ProductItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentProductGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
//        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentProductGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        fun bind(item: PlaceholderItem) {
//            binding.apply {
//                itemThumbnailImg.setImageResource(item.imageResId)
//                itemBrand.text = item.brand
//                itemName.text = item.name
//                itemPrice.text = item.price
//                // Set click listener for the ImageButton here if needed
//            }
//        }
    }
}
