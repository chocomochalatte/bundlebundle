package com.example.bundlebundle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.bundlebundle.databinding.ActivityCartBinding
import com.example.bundlebundle.retrofit.dataclass.CartVO
import com.example.bundlebundle.retrofit.service.CartApiService
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CartActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var myJangButton: TextView
    private lateinit var grourJangButton: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCartBinding.inflate(layoutInflater)
        myJangButton = binding.jangbaguniToolbar.myJangbaguni
        grourJangButton = binding.jangbaguniToolbar.groupJangbaguni
        val backButton = binding.jangbaguniToolbar.jangBack

        fragmentManager = supportFragmentManager

        myJangButton.isSelected = true
        myJangButton.setBackgroundResource(R.drawable.buttonunderline)

        myJangButton.setOnClickListener {
            myJangButton.setTextColor(ContextCompat.getColor(this, R.color.orange))
            grourJangButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            myJangButton.setBackgroundResource(R.drawable.buttonunderline)
            grourJangButton.background = null
            showMyJangFragments()
        }

        grourJangButton.setOnClickListener {
            grourJangButton.setTextColor(ContextCompat.getColor(this, R.color.orange))
            myJangButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            grourJangButton.setBackgroundResource(R.drawable.buttonunderline)
            myJangButton.background = null
            showGroupJangFragments()
        }

        backButton.setOnClickListener{
            onBackPressed()
        }

        // 초기 Fragment 추가
        showMyJangFragments()

        setContentView(binding.root)
    }

    private fun showMyJangFragments() {

        MyCartItemapiReqeust{
            myData ->
            if (myData != null && myData.cartCnt > 0) {
                //Fragment추가하는 부분 (이 부분은 장바구니 수량이 없는경우 조건을 걸어줘야함)
                var transaction = fragmentManager.beginTransaction()

                var fragment = CartTopBarFragment().newInstance(myData)
                transaction.replace(R.id.noMyCartItemfragment,fragment)

                val fragment1 = CartItemFragment.newInstance(myData)
                transaction.replace(R.id.item_cartfragment, fragment1)

                var fragment2 = CartBottomFragment()
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
        val transaction = fragmentManager.beginTransaction()

        val fragment = GroupCartTopBarFragment()
        transaction.replace(R.id.noMyCartItemfragment, fragment)

        val fragment1 = GroupCartItemFragment()
        transaction.replace(R.id.item_cartfragment, fragment1)

        val fragment2 = CartBottomFragment()
        transaction.replace(R.id.bottom_cartfragment, fragment2)

        transaction.commit()
    }

    private fun MyCartItemapiReqeust(callback: (myData: CartVO?) -> Unit){
        //1. retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/bundlebundle/api/cart/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //2. Service 객체 생성
        val apiService:CartApiService = retrofit.create(CartApiService::class.java)


        // Call 객체 생성
        val call = apiService.checkCart(1)

        // 4. 네트워크 통신
        call.enqueue(object: Callback<CartVO>{
            override fun onResponse(call: Call<CartVO>, response: Response<CartVO>) {
                val myData = response.body()
                callback(myData) // 콜백 함수 호출하여 myData 전달
            }

            override fun onFailure(call: Call<CartVO>, t: Throwable) {
                call.cancel()
                callback(null) // 실패 시 null을 전달
            }

        })
    }

}
