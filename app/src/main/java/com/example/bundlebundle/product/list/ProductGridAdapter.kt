package com.example.bundlebundle.product.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.bundlebundle.databinding.ItemProductGridBinding
import com.example.bundlebundle.product.detail.ProductDetailActivity
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import java.text.NumberFormat
import java.util.Locale

class ProductGridViewHolder(private val binding: ItemProductGridBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductVO) {
        binding.itemBrand.text = product.brand
        binding.itemName.text = product.name
        binding.itemPrice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(product.price)

        val requestOptions = RequestOptions()
            .override(500, 500)
            .encodeQuality(70)

        Glide.with(binding.itemThumbnailImg)
            .load(product.thumbnailImg)
            .apply(requestOptions)
            .into(binding.itemThumbnailImg)

        binding.itemLinearLayout.setOnClickListener {
            val context: Context = binding.root.context
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("productId", product.id)
            context.startActivity(intent)
        }
    }
}

class ProductGridAdapter(private val products: List<ProductVO>) : RecyclerView.Adapter<ProductGridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductGridViewHolder {
        val binding = ItemProductGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductGridViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductGridViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }
}
