package com.example.bundlebundle

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
import com.example.bundlebundle.databinding.FragmentCartRecyclerViewItemBinding
import com.example.bundlebundle.databinding.FragmentGroupCartItemBinding

class CartItemViewHolder1(val binding: FragmentGroupCartItemBinding): RecyclerView.ViewHolder(binding.root)

class CartItemAdapter1(var myData: MutableList<CartItem>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  CartItemViewHolder1(FragmentGroupCartItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return myData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var binding = (holder as CartItemViewHolder1).binding
        val currentItem = myData[position]
        Log.d("honga","$currentItem")
        binding.cartitemName1.text = currentItem.cartitem_name
        binding.cartitemOriginalprice1.text = currentItem.myjangitem_originalprice
        binding.cartitemDiscountprice1.text = currentItem.cartitem_discountprice
    }

}

class GroupCartItemFragment : Fragment() {

    private lateinit var binding: FragmentCartRecyclerViewItemBinding
    private var myData = mutableListOf<CartItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartRecyclerViewItemBinding.inflate(layoutInflater,container,false)
        val myData = mutableListOf(
            CartItem("10000", "1등급 한우", "80000"),
            CartItem("20000", "2등급 한우", "15000"),
            CartItem("30000", "3등급 한우", "15000")
        )

        binding.recyclercartItem3.layoutManager = LinearLayoutManager(requireContext())


        var myAdapter = CartItemAdapter1(myData)
        binding.recyclercartItem3.adapter = myAdapter
        Log.d("hong","왜 안와")

        return binding.root
    }
}