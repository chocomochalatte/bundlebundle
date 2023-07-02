package com.example.bundlebundle.product.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.bundlebundle.R
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import java.text.NumberFormat
import java.util.Locale

class ProductItemRecyclerViewAdapter(private val productList: List<ProductVO>) : RecyclerView.Adapter<ProductItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_product_grid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.bindProduct(product)
    }

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val productBrandTextView: TextView = view.findViewById(R.id.item_brand)
        private val productNameTextView: TextView = view.findViewById(R.id.item_name)
        private val productImageView: ImageView = view.findViewById(R.id.item_thumbnail_img)
        private val productPriceTextView: TextView = view.findViewById(R.id.item_price)

        fun bindProduct(product: ProductVO) {
            productBrandTextView.text = "[${product.brand}]"
            productNameTextView.text = product.name
            productPriceTextView.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(product.price)

            Glide.with(itemView)
                .load(product.thumbnailImg)
                .into(productImageView)
        }
    }
}