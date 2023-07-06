package com.example.bundlebundle.global

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bundlebundle.databinding.ActivityToastBinding
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.FBSApiClient
import com.example.bundlebundle.retrofit.dataclass.firebase.FcmData
import com.example.bundlebundle.retrofit.dataclass.firebase.FcmMessageVO
import com.example.bundlebundle.retrofit.dataclass.firebase.FcmResponse
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ToastActivity : AppCompatActivity() {
    lateinit var binding: ActivityToastBinding
    var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToastBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Token값 가져오면서 레트로핏 통신하기
        setToken { token, exception ->
            if (token != null) {
                Log.d("aaaa","$token")
                val fbsapiService = FBSApiClient.fbsapiService
                val fcmData = FcmData("그룹 장바구니 생성!!", "상품을 담으러 가보세요~~")
                val message = FcmMessageVO(token,"high",fcmData)
                // Call 객체 생성
                val call = fbsapiService.alarm(message)
                call.enqueue(object: Callback<FcmResponse>{
                    override fun onResponse(
                        call: Call<FcmResponse>,
                        response: Response<FcmResponse>
                    ) {
                        val responseData = response.body()
                        when(response.isSuccessful) {
                            true -> Log.d("aaaa","${responseData}")
                            else -> Log.d("bbbbb",response.code().toString() + response.errorBody().toString() + response.message())
                        }

                    }

                    override fun onFailure(call: Call<FcmResponse>, t: Throwable) {
                        call.cancel()
                    }
                })

            } else {
                // 예외 처리 로직
                Log.e("aaaa", "Failed to retrieve token: ${exception?.message}")
            }
        }

    }

    fun setToken(callback: (String?, Exception?) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    token = task.result
                    Log.d("aaaa","${token}")
                    callback(token, null)
                } else {
                    val exception = task.exception ?: Exception("Failed to retrieve token")
                    callback(null, exception)
                }
            }
    }

}
