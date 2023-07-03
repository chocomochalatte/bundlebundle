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
import com.example.bundlebundle.databinding.ActivityCartBinding
import com.example.bundlebundle.databinding.FragmentGroupCartBinding
import com.example.bundlebundle.databinding.FragmentGroupCartItemBinding


import com.example.bundlebundle.retrofit.dataclass.GroupCartListVO
import java.text.NumberFormat
import java.util.Locale

class GroupCartItemViewHolder(var binding: FragmentGroupCartItemBinding): RecyclerView.ViewHolder(binding.root)
class GroupCartItemAdapter(
    nickbinding: FragmentGroupCartBinding,
    private val noMyCartItemContainer: LinearLayout,
    private val itemCartContainer: LinearLayout,
    private val bottomCartContainer: LinearLayout,
    private val fragmentManager: FragmentManager,
    var groupData: GroupCartListVO
):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val nickbinding: FragmentGroupCartBinding = nickbinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GroupCartItemViewHolder(FragmentGroupCartItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return groupData.totalCnt
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var binding = (holder as GroupCartItemViewHolder).binding

        Log.d("honga","4 + ${groupData}")
        Log.d("honga","4 + ${groupData.totalCnt}")
        for (i in 0 until groupData.totalCnt) {
            val cartProduct = groupData.groupCart[i]
            Log.d("honga","3 + ${cartProduct}")
            nickbinding.groupcartNickname.text = cartProduct.groupNickname
            for ( j in 0 until cartProduct.cartCnt){

                Glide.with(binding.root.context)
                    .load(cartProduct.cartProducts[j].productThumbnailImg)  //이미지 URL 설정
                    .into(binding.groupitemImg)    //imageView에 넣기

                binding.groupitemItemCnt.text = cartProduct.cartProducts[j].productCnt.toString()

                binding.groupitemName.text = cartProduct.cartProducts[j].productName
                val OriginalPriceFormatted =
                    NumberFormat.getNumberInstance(Locale.getDefault()).format(cartProduct.cartProducts[j].productPrice)
                binding.groupitemOriginalprice.text = OriginalPriceFormatted

                val discountRate = cartProduct.cartProducts[j].discountRate / 100.0 // 비율로 변환
                val discountPrice = ((1 - discountRate) * cartProduct.cartProducts[j].productPrice).toInt()
                val discountPriceFormatted =
                    NumberFormat.getNumberInstance(Locale.getDefault()).format(discountPrice)
                binding.groupitemDiscountprice.text = discountPriceFormatted
            }
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
        arguments?.let {
            groupData = it.getParcelable(GROUP_CART_ITEM)!!

            val bind = ActivityCartBinding.inflate(layoutInflater)
            val noMyCartItemContainer = bind.noMyCartItemfragment
            val itemCartContainer = bind.itemCartfragment
            val bottomCartContainer = bind.bottomCartfragment

            groupAdapter = fragmentManager?.let { it1 ->
                GroupCartItemAdapter(
                    binding,
                    noMyCartItemContainer,
                    itemCartContainer,
                    bottomCartContainer,
                    it1,
                    groupData
                )
            }!!

            binding.groupcartItems.adapter=groupAdapter
        }

        binding.groupcartItems.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    fun newInstance(groupData: GroupCartListVO): GroupCartItemFragment {
        val fragment = GroupCartItemFragment()
        val args = Bundle().apply {
            putParcelable(GROUP_CART_ITEM, groupData)
        }
        fragment.arguments = args
        return fragment
    }

    companion object {
        private const val GROUP_CART_ITEM = "argMyData"
        fun newInstance(groupData: GroupCartListVO): GroupCartItemFragment {
            val fragment = GroupCartItemFragment()
            val args = Bundle().apply {
                putParcelable(GROUP_CART_ITEM, groupData)
            }
            fragment.arguments = args
            return fragment
        }
    }
}