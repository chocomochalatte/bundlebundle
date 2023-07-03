package com.example.bundlebundle.product.detail

import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.bundlebundle.template.UpbuttonTemplateActivity

import kotlin.properties.Delegates

class ProductDetailActivity : UpbuttonTemplateActivity() {

    private var productId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {

        productId = intent.getIntExtra("productId", -1)
        if (productId == -1) {
            finish()
            return
        }

        super.onCreate(savedInstanceState)
    }

    override fun setFragment(): Fragment {
        return ProductDetailFragment.newInstance(productId)
    }
}
