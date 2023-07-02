package com.example.bundlebundle.product.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bundlebundle.databinding.FragmentProductGridBinding
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import com.squareup.picasso.Picasso

class ProductItemRecyclerViewAdapter(
    private val values: List<ProductVO>
) : RecyclerView.Adapter<ProductItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 뷰 홀더를 생성하는 메서드
        val binding = FragmentProductGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 뷰 홀더와 데이터를 바인딩하는 메서드
        val item = values[position]
        holder.bind(item)

        holder.viewHolder = this
    }

    override fun getItemCount(): Int {
        // 아이템 개수를 반환하는 메서드
        return values?.size ?: 0
    }

    inner class ViewHolder(private val binding: FragmentProductGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // 뷰 홀더 내부 클래스
        var viewHolder: ProductItemRecyclerViewAdapter? = null

        fun bind(item: ProductVO) {
            // 아이템을 뷰에 바인딩하는 메서드
            binding.apply {
                Picasso.get().load(item.thumbnailImg).into(itemThumbnailImg)
                itemBrand.text = "[${item.brand}]"
                itemName.text = item.name
                itemPrice.text = item.price.toString()

                this@ViewHolder.viewHolder = this@ProductItemRecyclerViewAdapter
            }
        }
    }
}