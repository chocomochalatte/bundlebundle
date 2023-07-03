package com.example.bundlebundle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bundlebundle.databinding.FragmentCartBinding
import com.example.bundlebundle.databinding.FragmentCartItemBinding
import com.example.bundlebundle.retrofit.dataclass.CartProductVO
import com.example.bundlebundle.retrofit.dataclass.CartVO
import java.text.NumberFormat
import java.util.Locale


class CartItemViewHolder(val binding: FragmentCartItemBinding):RecyclerView.ViewHolder(binding.root)

class CartItemAdapter(var myData: CartVO, val onDeleteListener: CartItemFragment):RecyclerView.Adapter<RecyclerView.ViewHolder>(){



    private val cartProducts: MutableList<CartProductVO> = myData.cartProducts.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  CartItemViewHolder(FragmentCartItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return myData.cartProducts.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var binding = (holder as CartItemViewHolder).binding
        val currentItem = myData.cartProducts[position]
        val cnt = myData.cartCnt

        Glide.with(binding.root.context)
            .load(currentItem.productThumbnailImg)  //이미지 URL 설정
            .into(binding.mycartitemImg)    //imageView에 넣기

        binding.mycartitemName.text = currentItem.productName
        val OriginalPriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(currentItem.productPrice)
        binding.mycartitemOriginalprice.text = OriginalPriceFormatted

        val discountRate = currentItem.discountRate / 100.0 // 비율로 변환
        val discountPrice = ((1 - discountRate) * currentItem.productPrice).toInt()
        val discountPriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(discountPrice)
        binding.mycartitemDiscountprice.text = discountPriceFormatted

        val productCntFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(currentItem.productCnt)
        binding.mycartitemProductCnt.text = productCntFormatted

        binding.mycartitemDelete.setOnClickListener {
            val productId = currentItem.productId
            val memberId = currentItem.memberId
            onDeleteListener.onCartItemDeleted(productId,memberId)
        }
    }

    fun setOnCartItemDeleteListener(cartActivity: CartActivity) {

    }

    interface OnCartItemDeleteListener {
        fun onCartItemDeleted(productId: Int, memberId: Int)
    }


}



class CartItemFragment : Fragment(), CartItemAdapter.OnCartItemDeleteListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var myData:CartVO
    private lateinit var myAdapter: CartItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        arguments?.let {
            myData = it.getParcelable(MY_CART_ITEM)!!
            myAdapter = CartItemAdapter(myData,this)
            binding.recyclercartItem.adapter = myAdapter
            // RecyclerView 업데이트
            myAdapter.notifyDataSetChanged()
        }

        binding.recyclercartItem.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }


    companion object {
        private const val MY_CART_ITEM = "argMyData"
        fun newInstance(myData: CartVO): CartItemFragment {
            val fragment = CartItemFragment()
            val args = Bundle().apply {
                putParcelable(MY_CART_ITEM, myData)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCartItemDeleted(productId: Int, memberId: Int) {
        TODO("Not yet implemented")
    }


}