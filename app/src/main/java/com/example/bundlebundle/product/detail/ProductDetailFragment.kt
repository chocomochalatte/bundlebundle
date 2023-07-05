package com.example.bundlebundle.product.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.bundlebundle.databinding.FragmentProductDetailBinding
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.product.ProductVO
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale
import kotlin.properties.Delegates

class ProductDetailFragment: Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val productApiService = ApiClient.productApiService
    private var productId by Delegates.notNull<Int>()

    private lateinit var productImageView: ImageView
    private lateinit var productFullnameTextView: TextView
    private lateinit var productNameTextView: TextView
    private lateinit var productPriceTextView: TextView
    private lateinit var productOriginTextView: TextView
    private lateinit var productPackageTypeTextView: TextView

    private lateinit var goPurchaseButton: Button
    private lateinit var addCartButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        productId = arguments?.getInt(ProductDetailFragment.ARG_PRODUCT_ID) ?: -1
        getProductFromApi(productId)

        initializeViews()
        setupButtonListeners()
        getProductFromApi(productId)

        return binding.root
    }

    private fun initializeViews() {
        goPurchaseButton = binding.buttonGoPurchase
        addCartButton = binding.buttonAddCart
        productFullnameTextView = binding.productFullNameTextView
        productNameTextView = binding.productFullNameTextView
        productImageView = binding.productImageView
        productPriceTextView = binding.productPriceTextView
        productOriginTextView = binding.productOriginTextView
        productPackageTypeTextView = binding.productPackageTypeTextView

    }

    private fun setupButtonListeners() {
        goPurchaseButton.setOnClickListener {
            openBottomSheet(BottomSheetPurchaseFragment())
        }
        addCartButton.setOnClickListener {
            openBottomSheet(BottomSheetCartFragment())
        }
    }

    private fun getProductFromApi(productId: Int) {
        productApiService.showProductDetail(productId).enqueue(object : Callback<ProductVO> {
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
            productFullnameTextView.text = "[${it.brand}] ${it.name}"
            val priceFormatted: String = NumberFormat.getNumberInstance(Locale.getDefault()).format(it.price)
            productPriceTextView.text = priceFormatted
            productOriginTextView.text = it.origin
            productPackageTypeTextView.text = it.packageType
        }
    }

    private fun openBottomSheet(fragment: BottomSheetDialogFragment) {
        fragment.show(requireActivity().supportFragmentManager, "bottomSheet")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PRODUCT_ID = "productId"

        @JvmStatic
        fun newInstance(productId: Int): ProductDetailFragment {
            return ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PRODUCT_ID, productId)
                }
            }
        }
    }
}