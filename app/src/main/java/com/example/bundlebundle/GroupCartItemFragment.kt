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
import com.example.bundlebundle.databinding.FragmentCartContentBinding
import com.example.bundlebundle.databinding.FragmentGroupCartBinding
import com.example.bundlebundle.databinding.ItemGroupCartBinding
import com.example.bundlebundle.databinding.ItemGroupCartProductBinding
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.CartCheckVO
import com.example.bundlebundle.retrofit.dataclass.GroupCartChangeVO


import com.example.bundlebundle.retrofit.dataclass.GroupCartListVO
import com.example.bundlebundle.retrofit.dataclass.GroupCartProduct
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class GroupCartProductItemViewHolder(var binding: ItemGroupCartProductBinding): RecyclerView.ViewHolder(binding.root)
class GroupCartProductsAdapter(
    private val noMyCartItemContainer: LinearLayout,
    private val itemCartContainer: LinearLayout,
    private val bottomCartContainer: LinearLayout,
    private val fragmentManager: FragmentManager,
    private val cartProducts: List<GroupCartProduct>,
    private val memberId: Int,
    private val generalmemberId: Int,
    private val groupData: GroupCartListVO
): RecyclerView.Adapter<GroupCartProductItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupCartProductItemViewHolder {
        val binding = ItemGroupCartProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GroupCartProductItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartProducts.size
    }

    override fun onBindViewHolder(holder: GroupCartProductItemViewHolder, position: Int) {
        val binding = holder.binding
        val cartProduct = cartProducts[position]
        if(memberId == generalmemberId){
            Glide.with(binding.root.context)
                .load(cartProduct.productThumbnailImg)  //이미지 URL 설정
                .into(binding.groupitemImg)    //imageView에 넣기
            binding.groupitemCnt.text = cartProduct.productCnt.toString()
            binding.groupitemName.text = cartProduct.productName
            val OriginalPriceFormatted =
                NumberFormat.getNumberInstance(Locale.getDefault()).format(cartProduct.productPrice)
            binding.groupitemOriginalprice.text = OriginalPriceFormatted
            val discountRate = cartProduct.discountRate / 100.0 // 비율로 변환
            val discountPrice = ((1 - discountRate) * cartProduct.productPrice).toInt()
            val discountPriceFormatted =
                NumberFormat.getNumberInstance(Locale.getDefault()).format(discountPrice)
            binding.groupitemDiscountprice.text = discountPriceFormatted

            // 삭제버튼 누른 경우
            binding.groupitemDelete.setOnClickListener {
                val memberId = cartProduct.memberId
                val productId = cartProduct.productId
                val groupId = cartProduct.groupId
                deleteCartItem(memberId,productId,groupId)
            }

            // + 버튼 누른 경우
            binding.groupitemPlus.setOnClickListener {
                val memberId = cartProduct.memberId
                val productId = cartProduct.productId
                val groupId = cartProduct.groupId
                val productCnt = cartProduct.productCnt+1

                plusProductCnt(memberId,productId,groupId,productCnt)
            }

            // - 버튼 누른 경우
            binding.groupitemMinus.setOnClickListener {
                val memberId = cartProduct.memberId
                val productId = cartProduct.productId
                val groupId = cartProduct.groupId
                val productCnt = cartProduct.productCnt-1
                minusProductCnt(memberId,productId,groupId,productCnt)
            }

        }else{
            Glide.with(binding.root.context)
                .load(cartProduct.productThumbnailImg)  //이미지 URL 설정
                .into(binding.groupitemImg)    //imageView에 넣기
            binding.groupitemCnt.text = cartProduct.productCnt.toString()
            binding.groupitemName.text = cartProduct.productName
            binding.groupitemDelete.visibility = View.INVISIBLE
            binding.groupitemMinus.visibility = View.INVISIBLE
            binding.groupitemPlus.visibility = View.INVISIBLE
            val OriginalPriceFormatted =
                NumberFormat.getNumberInstance(Locale.getDefault()).format(cartProduct.productPrice)
            binding.groupitemOriginalprice.text = OriginalPriceFormatted
            val discountRate = cartProduct.discountRate / 100.0 // 비율로 변환
            val discountPrice = ((1 - discountRate) * cartProduct.productPrice).toInt()
            val discountPriceFormatted =
                NumberFormat.getNumberInstance(Locale.getDefault()).format(discountPrice)
            binding.groupitemDiscountprice.text = discountPriceFormatted
        }

    }

    private fun minusProductCnt(memberId: Int, productId: Int, groupId: Int, productCnt: Int) {
        val apiService = ApiClient.cartApiService

        val call = apiService.changeGroupCartItemCnt(productId, productCnt)

        call.enqueue(object : Callback<GroupCartChangeVO>{
            override fun onResponse(
                call: Call<GroupCartChangeVO>,
                response: Response<GroupCartChangeVO>
            ) {
                val apiService = ApiClient.cartApiService

                val call = apiService.groupcheckCart()

                call.enqueue(object : Callback<GroupCartListVO>{
                    override fun onResponse(
                        call: Call<GroupCartListVO>,
                        response: Response<GroupCartListVO>) {
                        val responseData = response.body()
                        Log.d("aaaa","aaa + ${responseData}")
                        // 프래그먼트를 다시 로딩
                        if (responseData != null) {
                            replaceFragments(responseData)
                        }
                        notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<GroupCartListVO>, t: Throwable) {
                        call.cancel()
                    }
                })
            }

            override fun onFailure(call: Call<GroupCartChangeVO>, t: Throwable) {
                call.cancel()
            }

        })
    }

    private fun plusProductCnt(memberId: Int, productId: Int, groupId: Int, productCnt: Int) {
        val apiService = ApiClient.cartApiService

        val call = apiService.changeGroupCartItemCnt(productId, productCnt)

        call.enqueue(object : Callback<GroupCartChangeVO>{
            override fun onResponse(
                call: Call<GroupCartChangeVO>,
                response: Response<GroupCartChangeVO>
            ) {
                val apiService = ApiClient.cartApiService

                val call = apiService.groupcheckCart()

                call.enqueue(object : Callback<GroupCartListVO>{
                    override fun onResponse(
                        call: Call<GroupCartListVO>,
                        response: Response<GroupCartListVO>) {
                        val responseData = response.body()

                        // 프래그먼트를 다시 로딩
                        if (responseData != null) {
                            replaceFragments(responseData)
                        }
                        notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<GroupCartListVO>, t: Throwable) {
                        call.cancel()
                    }
                })
            }
            override fun onFailure(call: Call<GroupCartChangeVO>, t: Throwable) {
                call.cancel()
            }
        })
    }



    private fun deleteCartItem(memberId: Int, productId: Int, groupId: Int) {
        val apiService = ApiClient.cartApiService

        val call = apiService.deleteGroupCartItem(productId)

        call.enqueue(object : Callback<CartCheckVO> {
            override fun onResponse(call: Call<CartCheckVO>, response: Response<CartCheckVO>) {
                val data = response.body()
                if (data?.exists == true) {
                    val apiService = ApiClient.cartApiService

                    val call = apiService.groupcheckCart()

                    call.enqueue(object : Callback<GroupCartListVO>{
                        override fun onResponse(
                            call: Call<GroupCartListVO>,
                            response: Response<GroupCartListVO>) {
                            val responseData = response.body()

                            // 프래그먼트를 다시 로딩
                            if (responseData != null) {
                                replaceFragments(responseData)
                            }
                            notifyDataSetChanged()
                        }

                        override fun onFailure(call: Call<GroupCartListVO>, t: Throwable) {
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

    private fun replaceFragments(groupData: GroupCartListVO) {
        val newTopBarFragment = groupData?.let { GroupCartTopBarFragment.newInstance(it) }
        val newItemFragment = groupData?.let { GroupCartItemFragment.newInstance(it) }
        val newBottomFragment = groupData?.let { GroupCartBottomFragment.newInstance(it) }

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



class GroupCartItemViewHolder(var binding: ItemGroupCartBinding): RecyclerView.ViewHolder(binding.root)
class GroupCartItemAdapter(
    private val noMyCartItemContainer: LinearLayout,
    private val itemCartContainer: LinearLayout,
    private val bottomCartContainer: LinearLayout,
    private val fragmentManager: FragmentManager,
    var groupData: GroupCartListVO
):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private lateinit var groupproductAdapter: GroupCartProductsAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GroupCartItemViewHolder(ItemGroupCartBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return groupData.totalCnt
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var binding = (holder as GroupCartItemViewHolder).binding
        binding.groupNickname.text = groupData.groupCart[position].groupNickname
        val loingmemberId = groupData.memberId
        val generalmemberId = groupData.groupCart[position].memberId
        val cartProductsAdapter = GroupCartProductsAdapter(noMyCartItemContainer,itemCartContainer,bottomCartContainer,fragmentManager,groupData.groupCart[position].cartProducts,loingmemberId,generalmemberId,groupData)
        binding.groupcartItemlist.adapter = cartProductsAdapter
        binding.groupcartItemlist.layoutManager = LinearLayoutManager(binding.root.context)
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

            val bind = FragmentCartContentBinding.inflate(layoutInflater)
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