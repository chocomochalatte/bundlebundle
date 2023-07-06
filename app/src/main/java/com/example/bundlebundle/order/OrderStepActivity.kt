package com.example.bundlebundle.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}
