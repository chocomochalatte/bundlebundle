package com.example.bundlebundle

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bundlebundle.databinding.FragmentCartBinding
import com.example.bundlebundle.databinding.FragmentCartItemBinding
import com.example.bundlebundle.retrofit.dataclass.CartVO
import com.example.bundlebundle.retrofit.service.CartApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class CartItemViewHolder(val binding: FragmentCartItemBinding):RecyclerView.ViewHolder(binding.root)

class CartItemAdapter(var myData: CartVO):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

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
        binding.mycartitemOriginalprice.text = currentItem.productPrice.toString()

        val discountRate = currentItem.discountRate / 100.0 // 비율로 변환
        val discountPrice = (1 - discountRate) * currentItem.productPrice
        val discountPriceInt = discountPrice.toInt() // 정수로 변환
        binding.mycartitemDiscountprice.text = discountPriceInt.toString()
        binding.mycartitemProductCnt.text = currentItem.productCnt.toString()
        }
    }

class CartItemFragment : Fragment() {

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

            myAdapter = CartItemAdapter(myData)
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


}