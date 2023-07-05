package com.example.bundlebundle.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bundlebundle.template.SimpleTemplateActivity

class OrderActivity : SimpleTemplateActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setFragment(): Fragment {
        return OrderListFragment()
    }

    override fun setTitle(): String {
        return "주문 내역"
    }
}