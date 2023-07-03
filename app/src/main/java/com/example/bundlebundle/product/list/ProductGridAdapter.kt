package com.example.bundlebundle.product.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bundlebundle.databinding.ItemProductGridBinding
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import java.text.NumberFormat
import java.util.Locale

class ProductGridViewHolder(val binding: ItemProductGridBinding): RecyclerView.ViewHolder(binding.root)

class ProductGridAdapter(private val products: List<ProductVO>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("ming", "onCreateViewHolder 실행")
        return ProductGridViewHolder(ItemProductGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("ming", "onBindViewHolder 실행")
        var binding = (holder as ProductGridViewHolder).binding
        binding.itemBrand.text = products[position].brand
        binding.itemName.text = products[position].name
        binding.itemPrice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(products[position].price)
        Glide.with(binding.itemThumbnailImg)
            .load(products[position].thumbnailImg)
            .into(binding.itemThumbnailImg)
    }

}