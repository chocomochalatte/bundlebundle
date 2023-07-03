package com.example.bundlebundle.product.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.bundlebundle.BottomSheetPurchaseFragment
import com.example.bundlebundle.R
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import com.example.bundlebundle.template.UpbuttonTemplateActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
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
