package com.example.bundlebundle
import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.example.bundlebundle.BuildConfig

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // KaKao SDK  초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}