package com.example.bundlebundle.retrofit.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// 코틀린의 object 키워드를 이용하여 싱글톤으로 생성해줍니다.
// baseUrl에는 연동할 서버 url을 넣어주며, addConverterFactory에는 GsonConverter를 추가하여
// JSON 형식을 Data Class 형식으로 자동변환해줍니다.
//지금 예시처럼 impl을 하지 않아도 된다

object GroupRetrofitServiceImpl {
    // 항상 URL에 /가 들어가야 한다
    private const val BASE_URL = "http://15.164.186.213:3000/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service:ExampleRetrofitService = retrofit.create(ExampleRetrofitService::class.java)
}