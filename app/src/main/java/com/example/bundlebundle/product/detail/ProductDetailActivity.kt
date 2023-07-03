package com.example.bundlebundle.product.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.bundlebundle.BottomSheetPurchaseFragment
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.ActivityProductDetailBinding
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.properties.Delegates

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var openBottomSheetButton: Button
    private lateinit var productImageView: ImageView
    private lateinit var productNameTextView: TextView
    private lateinit var productPriceTextView: TextView
    private lateinit var productOriginTextView: TextView
    private lateinit var productPackageTypeTextView: TextView

    private val apiService = ApiClient.apiService

    private var productId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productId = intent.getIntExtra("productId", -1)
        if (productId == -1) {
            finish()
            return
        }

        initializeViews(binding)
        setupButtonListeners()
        getProductFromApi(productId)
    }

    private fun initializeViews(binding: ActivityProductDetailBinding) {
        openBottomSheetButton = binding.buttonOpenBottomSheet
        productImageView = binding.productImageView
        productNameTextView = binding.productFullNameTextView
        productPriceTextView = binding.productPriceTextView
        productOriginTextView = binding.productOriginTextView
        productPackageTypeTextView = binding.productPackageTypeTextView
    }

    private fun setupButtonListeners() {
        openBottomSheetButton.setOnClickListener {
            openBottomSheet()
        }
    }

    private fun getProductFromApi(productId: Int) {
        apiService.showProductDetail(productId).enqueue(object : Callback<ProductVO> {
            override fun onResponse(call: Call<ProductVO>, response: Response<ProductVO>) {
                if (response.isSuccessful) {
                    val product = response.body()
                    bindProductData(product)
                } else {
                    Log.e("ProductDetailActivity", "Failed to fetch product from API.")
                }
            }

            override fun onFailure(call: Call<ProductVO>, t: Throwable) {
                Log.e("ProductDetailActivity", "API call failed.", t)
            }
        })
    }

    private fun bindProductData(product: ProductVO?) {
        product?.let {
            Glide.with(this).load(it.thumbnailImg).into(productImageView)
            productNameTextView.text = it.name
            val priceFormatted: String = NumberFormat.getNumberInstance(Locale.getDefault()).format(it.price)
            productPriceTextView.text = priceFormatted
            productOriginTextView.text = it.origin
            productPackageTypeTextView.text = it.packageType
        }
    }

    private fun openBottomSheet() {
        val bottomSheetFragment = BottomSheetPurchaseFragment()
        bottomSheetFragment.show(supportFragmentManager, "bottomSheet")
    }
}
