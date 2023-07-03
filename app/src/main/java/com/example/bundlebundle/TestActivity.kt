package com.example.bundlebundle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bundlebundle.databinding.ActivityTestBinding
import com.example.bundlebundle.retrofit.dataclass.Cart
import com.example.bundlebundle.retrofit.ApiService
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTestBinding.inflate(layoutInflater)
        binding.testbutton.setOnClickListener {
            testapiReqeust()
        }
        setContentView(binding.root)
    }

    private fun testapiReqeust(){

        //1. retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/bundlebundle/api/cart/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //2. Service 객체 생성
        val apiService: ApiService = retrofit.create(ApiService::class.java)


        // Call 객체 생성
        val call = apiService.getInfo(4)

        val request: Request = call.request()

        // Accept 헤더 설정
        request.newBuilder()
            .addHeader("Accept", "application/json")
            .build()

        // 4. 네트워크 통신
        call.enqueue(object: Callback<Cart>{
            override fun onResponse(call: Call<Cart>, response: Response<Cart>) {
                val info = response.body()

                if (info != null) {
                    Log.d("hong","${info.cartCnt}")
                }
            }

            override fun onFailure(call: Call<Cart>, t: Throwable) {
                // 호출 데이터
                call.cancel()
            }

        })
    }
}
