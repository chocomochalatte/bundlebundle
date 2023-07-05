package com.example.bundlebundle.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bundlebundle.R
import com.example.bundlebundle.template.SimpleTemplateActivity

class OrderActivity : SimpleTemplateActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
    }

    override fun setFragment(): Fragment {
        return OrderListFragment()
    }

    override fun setTitle(): String {
        "주문 내역"
    }
}