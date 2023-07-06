package com.example.bundlebundle.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.bundlebundle.R

class OrderStepActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_step)

        var searchBtn = findViewById<androidx.appcompat.widget.AppCompatImageButton>(R.id.searchAddressBtn)
        searchBtn.setOnClickListener{
            val intent = Intent(this@OrderStepActivity,OrderAddressActivity::class.java)
            startActivity(intent)
        }

        val intentData = intent.getStringExtra("addressText")
        var location = findViewById<TextView>(R.id.locationText)
        if (intentData != null) {
            location.setText(intentData)
        } else {
            location.setText("주소를 입력해주세요")
        }
    }
}
