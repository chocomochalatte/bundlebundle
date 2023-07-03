package com.example.bundlebundle.product.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.ItemProductGridBinding
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import java.text.NumberFormat
import java.util.Locale

class ProductGridViewHolder(val binding: ItemProductGridBinding): RecyclerView.ViewHolder(binding.root)

class ProductGridAdapter(private val products: List<ProductVO>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductGridViewHolder(ItemProductGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var binding = (holder as ProductGridViewHolder).binding
        binding.itemBrand.text = products[position].brand
        binding.itemName.text = products[position].name
        binding.itemPrice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(products[position].price)

        val requestOptions = RequestOptions()
            .override(500, 500)
            .encodeQuality(70)

        Glide.with(binding.itemThumbnailImg)
            .load(products[position].thumbnailImg)
            .apply(requestOptions) // RequestOptions 적용
            .into(binding.itemThumbnailImg)

    }

}