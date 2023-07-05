package com.example.bundlebundle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bundlebundle.databinding.FragmentCartBinding
import com.example.bundlebundle.databinding.FragmentCartContentBinding
import com.example.bundlebundle.databinding.FragmentCartItemBinding
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.CartChangeVO
import com.example.bundlebundle.retrofit.dataclass.CartCheckVO
import com.example.bundlebundle.retrofit.dataclass.CartProductVO
import com.example.bundlebundle.retrofit.dataclass.CartVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale


class CartItemViewHolder(val binding: FragmentCartItemBinding):RecyclerView.ViewHolder(binding.root)

class CartItemAdapter(private val noMyCartItemContainer: LinearLayout,
                      private val itemCartContainer: LinearLayout,
                      private val bottomCartContainer: LinearLayout,
                      private val fragmentManager: FragmentManager,
                      var myData: CartVO):RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val cartProducts: MutableList<CartProductVO> = myData.cartProducts.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CartItemViewHolder(
            FragmentCartItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
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
        val OriginalPriceFormatted =
            NumberFormat.getNumberInstance(Locale.getDefault()).format(currentItem.productPrice)
        binding.mycartitemOriginalprice.text = OriginalPriceFormatted

        val discountRate = currentItem.discountRate / 100.0 // 비율로 변환
        val discountPrice = ((1 - discountRate) * currentItem.productPrice).toInt()
        val discountPriceFormatted =
            NumberFormat.getNumberInstance(Locale.getDefault()).format(discountPrice)
        binding.mycartitemDiscountprice.text = discountPriceFormatted

        val productCntFormatted =
            NumberFormat.getNumberInstance(Locale.getDefault()).format(currentItem.productCnt)
        binding.mycartitemProductCnt.text = productCntFormatted

        // 삭제버튼 누른 경우
        binding.mycartitemDelete.setOnClickListener {
            val productId = currentItem.productId
            val memberId = currentItem.memberId
            deleteCartItem(memberId, productId)
        }

        // + 버튼 누른 경우
        binding.mycartitemPlus.setOnClickListener {
            val productId = currentItem.productId
            val memberId = currentItem.memberId
            val productCnt = currentItem.productCnt+1

            plusProductCnt(memberId,productId,productCnt)
        }

        // - 버튼 누른 경우
        binding.mycartitemMinus.setOnClickListener {
            val productId = currentItem.productId
            val memberId = currentItem.memberId
            val productCnt = currentItem.productCnt-1
            minusProductCnt(memberId,productId,productCnt)
        }
    }



    private fun plusProductCnt(memberId: Int, productId: Int, productCnt: Int) {
        val apiService = ApiClient.cartApiService

        // Call 객체 생성
        val call = apiService.changeCartItemCnt(memberId, productId, productCnt)


        call.enqueue(object : Callback<CartChangeVO>{
            override fun onResponse(call: Call<CartChangeVO>, response: Response<CartChangeVO>) {
                val myData = response.body()
                if(myData!=null){
                    val apiService = ApiClient.cartApiService

                    val call = apiService.checkCart(memberId)

                    call.enqueue(object : Callback<CartVO> {
                        override fun onResponse(call: Call<CartVO>, response: Response<CartVO>) {
                            val responseData = response.body()
                            // 프래그먼트를 다시 로딩
                            replaceFragments(responseData)
                            notifyDataSetChanged()
                        }
                        override fun onFailure(call: Call<CartVO>, t: Throwable) {
                            call.cancel()
                        }
                    })
                }else{

                }

            }
            override fun onFailure(call: Call<CartChangeVO>, t: Throwable) {
                call.cancel()
            }
        })
    }

    private fun minusProductCnt(memberId: Int, productId: Int, productCnt: Int) {
        val apiService = ApiClient.cartApiService

        // Call 객체 생성
        val call = apiService.changeCartItemCnt(memberId, productId, productCnt)

        call.enqueue(object : Callback<CartChangeVO>{
            override fun onResponse(call: Call<CartChangeVO>, response: Response<CartChangeVO>) {
                val myData = response.body()
                if(myData!=null){
                    val apiService = ApiClient.cartApiService

                    val call = apiService.checkCart(memberId)

                    call.enqueue(object : Callback<CartVO> {
                        override fun onResponse(call: Call<CartVO>, response: Response<CartVO>) {
                            val responseData = response.body()
                            // 프래그먼트를 다시 로딩
                            replaceFragments(responseData)
                            notifyDataSetChanged()
                        }
                        override fun onFailure(call: Call<CartVO>, t: Throwable) {
                            call.cancel()
                        }
                    })
                }else{

                }

            }
            override fun onFailure(call: Call<CartChangeVO>, t: Throwable) {
                call.cancel()
            }
        })
    }

    private fun deleteCartItem(memberId: Int, productId: Int) {
        //1. retrofit 객체 생성
        val apiService = ApiClient.cartApiService

        // Call 객체 생성
        val call = apiService.deleteCartItem(memberId, productId)

        call.enqueue(object : Callback<CartCheckVO> {
            override fun onResponse(call: Call<CartCheckVO>, response: Response<CartCheckVO>) {
                val data = response.body()
                if (data?.exists == true) {
                    val apiService = ApiClient.cartApiService

                    val call = apiService.checkCart(memberId)

                    call.enqueue(object : Callback<CartVO> {
                        override fun onResponse(call: Call<CartVO>, response: Response<CartVO>) {
                            val responseData = response.body()
                            SharedMyCartItem.myData = responseData
                            if (responseData != null) {
                                myData = responseData
                            }
                            // 프래그먼트를 다시 로딩
                            replaceFragments(responseData)
                            notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<CartVO>, t: Throwable) {
                            call.cancel()
                        }
                    })
                }
            }

            override fun onFailure(call: Call<CartCheckVO>, t: Throwable) {
                call.cancel()
            }
        })
    }

    private fun replaceFragments(myData: CartVO?) {
        // Create new instances of the fragments with the updated data
        val newTopBarFragment = myData?.let { CartTopBarFragment.newInstance(it) }
        val newItemFragment = myData?.let { CartItemFragment.newInstance(it) }
        val newBottomFragment = myData?.let { CartBottomFragment.newInstance(it) }
        val newEmptyCartFragment = EmptyCartFragment()
        if (myData?.cartCnt == 0) {
            if (noMyCartItemContainer != null && newTopBarFragment != null) {
                fragmentManager.beginTransaction()
                    .replace(noMyCartItemContainer.id, newEmptyCartFragment)
                    .commit()
            }
            if (itemCartContainer != null && newItemFragment != null) {
                fragmentManager.beginTransaction()
                    .replace(noMyCartItemContainer.id, newItemFragment)
                    .commit()
            }
            if (bottomCartContainer != null && newBottomFragment != null) {
                fragmentManager.beginTransaction()
                    .replace(noMyCartItemContainer.id, newBottomFragment)
                    .commit()
            }

        } else {
            // Replace the fragments if the corresponding container is available
            if (noMyCartItemContainer != null && newTopBarFragment != null) {
                fragmentManager.beginTransaction()
                    .replace(noMyCartItemContainer.id, newTopBarFragment)
                    .commit()
            }
            if (itemCartContainer != null && newItemFragment != null) {
                fragmentManager.beginTransaction()
                    .replace(itemCartContainer.id, newItemFragment)
                    .commit()
            }
            if (bottomCartContainer != null && newBottomFragment != null) {
                fragmentManager.beginTransaction()
                    .replace(bottomCartContainer.id, newBottomFragment)
                    .commit()
            }
        }


    }


    class CartItemFragment : Fragment() {

        private lateinit var binding: FragmentCartBinding
        private lateinit var myData: CartVO
        private lateinit var myAdapter: CartItemAdapter


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentCartBinding.inflate(inflater, container, false)
            arguments?.let {
                myData = it.getParcelable(MY_CART_ITEM)!!

                val bind = FragmentCartContentBinding.inflate(layoutInflater)
                val noMyCartItemContainer = bind.noMyCartItemfragment
                val itemCartContainer = bind.itemCartfragment
                val bottomCartContainer = bind.bottomCartfragment
                myAdapter = fragmentManager?.let { it1 ->
                    CartItemAdapter(
                        noMyCartItemContainer,
                        itemCartContainer,
                        bottomCartContainer,
                        it1,
                        myData
                    )
                }!!
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
}