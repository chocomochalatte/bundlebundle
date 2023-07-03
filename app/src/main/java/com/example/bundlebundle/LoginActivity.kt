package com.example.bundlebundle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bundlebundle.retrofit.dataclass.member.LoginTokenVO
import com.example.bundlebundle.retrofit.service.ApiService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //컨텍스트 얻기
        val context: Context = this
        var mContext: Context? = null
        fun setContext(context: Context) {
            mContext = context
        }
        val kakaoLoginButton = findViewById<android.widget.Button>(R.id.oauth_login)
        // 카카오 로그인
        kakaoLoginButton.setOnClickListener{

            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    val retrofit: Retrofit = Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080/bundlebundle/api/member/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    val apiService: ApiService = retrofit.create(ApiService::class.java)
                    val call: Call<LoginTokenVO> = apiService.gettoken(token.accessToken)
                    call.enqueue(object : Callback<LoginTokenVO> {
                        override fun onResponse(call: Call<LoginTokenVO>, response: Response<LoginTokenVO>) {
                            if (response.isSuccessful) {
                                val tokenInfo = response.body()
                                tokenInfo?.let { info ->
                                    // 서버 응답 처리
                                    Log.i("RealLOGIN", "CHECK $info")
                                    Log.i("RealLOGIN", "카카오계정으로 로그인 성공 : 엑세스 토큰 ${token.accessToken}")
                                } ?: run {
                                    // 응답이 null인 경우 처리
                                    Log.e("RealLOGIN", "서버 응답이 null입니다.")
                                }
                            } else {
                                // 응답이 실패한 경우 처리
                                Log.e("RealLOGIN", "서버 응답이 실패했습니다. 상태 코드: ${response.code()}")
                            }
                        }

                        override fun onFailure(call: Call<LoginTokenVO>, t: Throwable) {
                            Log.e("RealLOGIN", "서버 응답이 실패했습니다. 상태 코드: ${t.printStackTrace()}")
                        }
                    })

                    Log.i("RealLOGIN", "카카오계정으로 로그인 성공 : 엑세스 토큰 ${token.accessToken}")
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        val intent = Intent(mContext, MainActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

                        finish()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }

    fun kakaoLogout() {
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("Hello", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                Log.i("Hello", "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    fun kakaoUnlink() {
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e("Hello", "연결 끊기 실패", error)
            } else {
                Log.i("Hello", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
        finish()
    }

}

