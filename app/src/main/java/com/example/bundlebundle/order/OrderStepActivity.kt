package com.example.bundlebundle.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bundlebundle.R
import com.example.bundlebundle.order.retrofit.AddressApiClient
import com.example.bundlebundle.order.retrofit.dataclass.KakaoAddressResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderStepActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_step)


        val basicLoginButton = findViewById<android.widget.Button>(R.id.buttonSearch)

        basicLoginButton.setOnClickListener {
            val AddressApiService = AddressApiClient.addressApiService
            Log.d("apiTest22", "레트로핏 인스턴스를 만들었어요");
            AddressApiService.searchAddress("혜화");
            Log.d("apiTest22", "혜화 검색시작");
            val call: Call<KakaoAddressResponse> = AddressApiService.searchAddress("혜화");
            Log.d("apiTest22", "1");
            call.enqueue(object : Callback<KakaoAddressResponse> {
                override fun onResponse(
                    call: Call<KakaoAddressResponse>,
                    response: Response<KakaoAddressResponse>
                ) {
                    Log.d("apiTest22", "3");
                    if (response.isSuccessful) {
                        val addressList = response.body().toString()
                        Log.d("apiTest22", addressList);
                        // 주소 검색 결과를 처리합니다.
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.d("apiTest22", "API 실패 $errorBody");
                    }
                }

                override fun onFailure(call: Call<KakaoAddressResponse>, t: Throwable) {
                    Log.d("apiTest22", "네트워크 오류입니다");
                }
            })

        }
    }
}