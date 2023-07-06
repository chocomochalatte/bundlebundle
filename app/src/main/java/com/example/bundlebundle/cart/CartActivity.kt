package com.example.bundlebundle.cart

import androidx.fragment.app.Fragment
import com.example.bundlebundle.template.SimpleTemplateActivity

class CartActivity : SimpleTemplateActivity() {

    override fun setFragment(): Fragment {
        return CartContentFragment()
    }

    override fun setTitle(): String {
        return "장바구니"
    }
}
