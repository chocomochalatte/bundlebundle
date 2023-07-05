package com.example.bundlebundle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bundlebundle.databinding.FragmentOrderListBinding
import com.example.bundlebundle.databinding.ItemOrderListBinding
import com.example.bundlebundle.retrofit.dataclass.OrderVO

class OrderViewListHolder(val binding: ItemOrderListBinding): RecyclerView.ViewHolder(binding.root)

class OrderViewAdapter(var orderData:List<OrderVO>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrderViewListHolder(ItemOrderListBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return orderData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var binding = (holder as OrderViewListHolder).binding
        Glide.with(binding.root.context)
            .load(orderData[position].productThumbnailImg)  //이미지 URL 설정
            .into(binding.orderlistImg)    //imageView에 넣기
        binding.orderlistName.text = orderData[position].name
        binding.orderlistPrice.text = orderData[position].price.toString()
        binding.orderlistProductcnt.text = orderData[position].productCnt.toString()
    }

}

class OrderListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrderListBinding.inflate(layoutInflater)
        return binding.root
    }

}