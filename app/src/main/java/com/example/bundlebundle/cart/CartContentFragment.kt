package com.example.bundlebundle.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bundlebundle.CartBottomFragment
import com.example.bundlebundle.CartItemAdapter
import com.example.bundlebundle.CartTopBarFragment
import com.example.bundlebundle.EmptyCartFragment
import com.example.bundlebundle.EmptyGroupCartFragment
import com.example.bundlebundle.GroupCartBottomFragment
import com.example.bundlebundle.GroupCartItemFragment
import com.example.bundlebundle.GroupCartTopBarFragment
import com.example.bundlebundle.R
import com.example.bundlebundle.SharedMyCartItem
import com.example.bundlebundle.databinding.FragmentCartContentBinding
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.CartVO
import com.example.bundlebundle.retrofit.dataclass.GroupCartListVO
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartContentFragment : Fragment() {
    private var _binding: FragmentCartContentBinding? = null
    private val binding get() = _binding!!

    private lateinit var fragmentManager: FragmentManager

    private lateinit var cartTab: TabLayout
    private lateinit var intent: Intent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentManager = parentFragmentManager
        intent = requireActivity().intent

        cartTab = binding.tabLayout
        setTab(cartTab)
        setTabListeners()

        val startingTab: String = intent.getStringExtra("tab") ?: "personal"
        setStartingTab(startingTab)
    }

    private fun setTab(cartTab: TabLayout) {
        val tabPersonalCart = cartTab.newTab()
        tabPersonalCart.text = "개인 장바구니"
        val tabGroupCart = cartTab.newTab()
        tabGroupCart.text = "그룹 장바구니"

        cartTab.addTab(tabPersonalCart)
        cartTab.addTab(tabGroupCart)
    }

    private fun setStartingTab(startingTab: String) {
        val tabIndex = when (startingTab) {
            "group" -> 1
            "personal" -> 0
            else -> 0
        }
        cartTab.getTabAt(tabIndex)?.select()
    }

    private fun setTabListeners() {
        val tabListener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val position = it.position
                    when (position) {
                        0 -> {  // 첫 번째 탭이 선택되었을 때의 동작
                            showMyJangFragments()
                        }
                        1 -> {  // 두 번째 탭이 선택되었을 때의 동작
                            showGroupJangFragments()
                        }
                        else -> showMyJangFragments()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택 해제되었을 때의 동작
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택되었을 때의 동작
            }
        }

// TabLayout에 탭 선택 리스너 등록
        cartTab.addOnTabSelectedListener(tabListener)
    }

    private fun showMyJangFragments() {

        MyCartItemapiReqeust{
                myData ->
            if (myData != null && myData.cartCnt > 0) {
                //Fragment추가하는 부분 (이 부분은 장바구니 수량이 없는경우 조건을 걸어줘야함)
                var transaction = fragmentManager.beginTransaction()

                var fragment = CartTopBarFragment().newInstance(myData)
                transaction.replace(R.id.noMyCartItemfragment,fragment)

                val fragment1 = CartItemAdapter.CartItemFragment.newInstance(myData)
                transaction.replace(R.id.item_cartfragment, fragment1)

                var fragment2 = CartBottomFragment().newInstance(myData)
                transaction.replace(R.id.bottom_cartfragment,fragment2)
                transaction.commit()

            }else{
                var transaction = fragmentManager.beginTransaction()
                var fragment = EmptyCartFragment()
                transaction.replace(R.id.noMyCartItemfragment,fragment)
                transaction.commit()
            }
        }
    }

    private fun showGroupJangFragments() {
        GroupCartItemapiReqeust{
                groupData ->
            if(groupData != null && groupData.totalCnt > 0){
                val transaction = fragmentManager.beginTransaction()

                val fragment = GroupCartTopBarFragment().newInstance(groupData)
                transaction.replace(R.id.noMyCartItemfragment, fragment)

                val fragment1 = GroupCartItemFragment().newInstance(groupData)
                transaction.replace(R.id.item_cartfragment, fragment1)

                val fragment2 = GroupCartBottomFragment().newInstance(groupData)
                transaction.replace(R.id.bottom_cartfragment, fragment2)

                transaction.commit()
            }else{
                var transaction = fragmentManager.beginTransaction()
                var fragment = EmptyGroupCartFragment()
                transaction.replace(R.id.noMyCartItemfragment,fragment)
                transaction.commit()
            }
        }
    }

    private fun MyCartItemapiReqeust(callback: (myData: CartVO?) -> Unit){
        //1. retrofit 객체 생성
        val apiService = ApiClient.cartApiService

        // Call 객체 생성
        val call = apiService.checkCart(1)

        // 4. 네트워크 통신
        call.enqueue(object: Callback<CartVO> {
            override fun onResponse(call: Call<CartVO>, response: Response<CartVO>) {
                val myData = response.body()
                SharedMyCartItem.myData = myData
                callback(myData) // 콜백 함수 호출하여 myData 전달
            }

            override fun onFailure(call: Call<CartVO>, t: Throwable) {
                call.cancel()
                callback(null) // 실패 시 null을 전달
            }
        })
    }

    private fun GroupCartItemapiReqeust(callback: (groupData: GroupCartListVO?) -> Unit){
        //1. retrofit 객체 생성
        val apiService = ApiClient.cartApiService
        val groupId = intent.getIntExtra("groupId", -1)
        val call = apiService.groupcheckCart(groupId)

        // 4. 네트워크 통신
        call.enqueue(object: Callback<GroupCartListVO>{
            override fun onResponse(call: Call<GroupCartListVO>, response: Response<GroupCartListVO>) {
                val groupData = response.body()
                callback(groupData) // 콜백 함수 호출하여 myData 전달
            }

            override fun onFailure(call: Call<GroupCartListVO>, t: Throwable) {
                call.cancel()
                callback(null) // 실패 시 null을 전달
            }

        })
    }

}