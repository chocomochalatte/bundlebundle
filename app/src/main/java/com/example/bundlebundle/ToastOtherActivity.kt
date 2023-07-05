package com.example.bundlebundle

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast

class ToastOtherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toast()
        setContentView(R.layout.activity_toast_other)
    }

    @SuppressLint("MissingInflatedId")
    private fun toast() {
        var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_view_holder,null)
        var text : TextView = layoutInflater.findViewById(R.id.TextViewToast)
        text.text="장바구니를 생성하였습니다."
        var toast = Toast(this)
        toast.view = layoutInflater
        toast.show()
    }
}