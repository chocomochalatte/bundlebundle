package com.example.bundlebundle

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bundlebundle.databinding.FragmentCartBinding
import com.example.bundlebundle.databinding.FragmentCartItemBinding

data class CartItem(val myjangitem_originalprice: String, val cartitem_name: String, val cartitem_discountprice: String)

class CartItemViewHolder(val binding: FragmentCartItemBinding):RecyclerView.ViewHolder(binding.root)

class CartItemAdapter(var myData: MutableList<CartItem>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  CartItemViewHolder(FragmentCartItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return myData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var binding = (holder as CartItemViewHolder).binding
        val currentItem = myData[position]
        Log.d("hong","$currentItem")
        binding.cartitemName.text = currentItem.cartitem_name
        binding.cartitemOriginalprice.text = currentItem.myjangitem_originalprice
        binding.cartitemDiscountprice.text = currentItem.cartitem_discountprice
    }

}

class CartItemFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private var myData = mutableListOf<CartItem>()


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        val myData = mutableListOf(
            CartItem("10000", "1등급 한우", "80000"),
            CartItem("20000", "2등급 한우", "15000"),
            CartItem("30000", "3등급 한우", "15000")
        )

        binding.recyclercartItem.layoutManager = LinearLayoutManager(requireContext())

        var myAdapter = CartItemAdapter(myData)
        binding.recyclercartItem.adapter = myAdapter


        return binding.root
    }
}