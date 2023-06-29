package com.example.bundlebundle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bundlebundle.databinding.FragmentGroupCartBinding
import com.example.bundlebundle.databinding.FragmentGroupCartItemBinding

data class GroupItem(val nickname: String, val myjangitem_originalprice: String, val cartitem_name: String, val cartitem_discountprice: String)

class GroupCartItemViewHolder(var binding: FragmentGroupCartItemBinding): RecyclerView.ViewHolder(binding.root)

class GroupCartItemAdapter(var data: MutableList<GroupItem>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GroupCartItemViewHolder(FragmentGroupCartItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var binding = (holder as GroupCartItemViewHolder).binding
        val currentItem = data[position]
        Log.d("honga","$currentItem")
        binding.groupName.text=currentItem.nickname
        binding.cartitemName.text = currentItem.cartitem_name
        binding.cartitemOriginalprice.text = currentItem.myjangitem_originalprice
        binding.cartitemDiscountprice.text = currentItem.cartitem_discountprice
    }

}

class GroupCartItemFragment : Fragment() {

    private lateinit var binding: FragmentGroupCartBinding
    private var data = mutableListOf<GroupItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupCartBinding.inflate(layoutInflater)

        data = mutableListOf(
            GroupItem("ming","10000", "1등급 한우", "80000"),
            GroupItem("ming","20000", "2등급 한우", "15000"),
            GroupItem("choi","30000", "3등급 한우", "15000"),
            GroupItem("hong","30000", "3등급 한우", "15000")
        )

        binding.recyclercartItem3.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclercartItem4.layoutManager = LinearLayoutManager(requireContext())

        var mAdapter1 = GroupCartItemAdapter(data)
        binding.recyclercartItem3.adapter = mAdapter1


        return binding.root
    }


}