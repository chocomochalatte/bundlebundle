package com.example.bundlebundle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bundlebundle.SharedMyCartItem.myData
import com.example.bundlebundle.databinding.ActivityCartBinding
import com.example.bundlebundle.databinding.FragmentCartBinding
import com.example.bundlebundle.databinding.FragmentGroupCartBinding
import com.example.bundlebundle.databinding.FragmentGroupCartItemBinding
import com.example.bundlebundle.retrofit.dataclass.CartVO
import com.example.bundlebundle.retrofit.dataclass.GroupCartListVO


class GroupCartItemViewHolder(var binding: FragmentGroupCartItemBinding): RecyclerView.ViewHolder(binding.root)

class GroupCartItemAdapter(private val noMyCartItemContainer: LinearLayout,
                           private val itemCartContainer: LinearLayout,
                           private val bottomCartContainer: LinearLayout,
                           private val fragmentManager: FragmentManager,
                           var groupData: GroupCartListVO):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GroupCartItemViewHolder(FragmentGroupCartItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return groupData.totalCnt
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var binding = (holder as GroupCartItemViewHolder).binding
        val currentItem = groupData.groupCart[position]
        Log.d("honga","$currentItem")

        binding.groupNickname.text = currentItem.groupNickname
        binding.groupitemItemCnt.text = currentItem.cartCnt.toString()

        for (i in 0 until currentItem.cartProducts.size) {
            val cartProduct = currentItem.cartProducts[i]
            Glide.with(binding.root.context)
                .load(cartProduct.productThumbnailImg)  //이미지 URL 설정
                .into(binding.groupitemImg)    //imageView에 넣기
            binding.groupitemName.text = cartProduct.productName
            binding.groupitemOriginalprice.text = cartProduct.productPrice.toString()
            binding.groupitemDiscountprice.text = cartProduct.
        }
    }

}

class GroupCartItemFragment : Fragment() {

    private lateinit var binding: FragmentGroupCartBinding
    private lateinit var groupData: GroupCartListVO
    private lateinit var groupAdapter: GroupCartItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupCartBinding.inflate(inflater, container, false)


        val bind = ActivityCartBinding.inflate(layoutInflater)
        val noMyCartItemContainer = bind.noMyCartItemfragment
        val itemCartContainer = bind.itemCartfragment
        val bottomCartContainer = bind.bottomCartfragment

        groupAdapter = fragmentManager?.let { it1 ->
            GroupCartItemAdapter(
                noMyCartItemContainer,
                itemCartContainer,
                bottomCartContainer,
                it1,
                groupData
            )
        }!!

        binding.groupcartNickname.adapter = groupAdapter
        binding.groupcartItems.adapter = groupAdapter


        binding.groupcartNickname.layoutManager = LinearLayoutManager(requireContext())
        binding.groupcartItems.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }


}