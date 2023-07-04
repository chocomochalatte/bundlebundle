package com.example.bundlebundle

import androidx.fragment.app.Fragment
import com.example.bundlebundle.cart.CartContentFragment
import com.example.bundlebundle.template.SimpleTemplateActivity
import kotlin.properties.Delegates

class CartActivity : SimpleTemplateActivity() {

    private var productId by Delegates.notNull<Int>()
    private var productCnt by Delegates.notNull<Int>()

    override fun setFragment(): Fragment {
        return CartContentFragment()
    }

    override fun setTitle(): String {
        return "장바구니"
    }
}