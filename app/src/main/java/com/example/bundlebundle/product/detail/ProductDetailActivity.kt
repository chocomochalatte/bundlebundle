package com.example.bundlebundle.product.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.bundlebundle.BottomSheetPurchaseFragment
import com.example.bundlebundle.LoginActivity
import com.example.bundlebundle.PreferenceUtil
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.ActivityProductDetailBinding
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.ApiService
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.util.Locale
import kotlin.properties.Delegates

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    private lateinit var openBottomSheetButton: Button

    private val apiService = ApiClient.apiService

    private var productId = 15

    private lateinit var productImageView: ImageView
    private lateinit var productNameTextView: TextView
    private lateinit var productPriceTextView: TextView
    private lateinit var productOriginTextView: TextView
    private lateinit var productPackageTypeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        // productId 추출
//        productId = intent.getIntExtra("productId", -1)
//        if (productId == -1) {
//            // productId를 전달받지 못한 경우 처리 로직
//            finish() // 예를 들어, productId가 없으면 액티비티를 종료할 수도 있습니다.
//            return
//        }

        openBottomSheetButton = findViewById(R.id.button_open_bottom_sheet)
        openBottomSheetButton.setOnClickListener {
            openBottomSheet()
        }

        productImageView = findViewById(R.id.product_image_view)
        productNameTextView = findViewById(R.id.product_full_name_text_view)
        productPriceTextView = findViewById(R.id.product_price_text_view)
        productOriginTextView = findViewById(R.id.product_origin_text_view)
        productPackageTypeTextView = findViewById(R.id.product_package_type_text_view)

        // Get the ProductVO object from the API or other source
        val product: ProductVO = getProductFromApi(productId)

        // Bind the data to the views
        bindProductData(product)
    }

    private fun getProductFromApi(productId: Int): ProductVO {
        val product: ProductVO = runBlocking {
            apiService.showProductDetail(productId)
        }

        return product
    }

    private fun bindProductData(product: ProductVO) {
        // Bind the data to the views
        Glide.with(this).load(product.thumbnailImg).into(productImageView)
        productNameTextView.text = product.name

        val priceFormatted: String = NumberFormat.getNumberInstance(Locale.getDefault()).format(product.price)
        productPriceTextView.text = priceFormatted

        productOriginTextView.text = product.origin
        productPackageTypeTextView.text = product.packageType
    }

    private fun openBottomSheet() {
        val bottomSheetFragment = BottomSheetPurchaseFragment()
        bottomSheetFragment.show(supportFragmentManager, "bottomSheet")
    }
}
