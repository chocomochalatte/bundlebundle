package com.example.bundlebundle.product.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.bundlebundle.BottomSheetPurchaseFragment
import com.example.bundlebundle.databinding.ActivityBaseBinding
import com.example.bundlebundle.databinding.FragmentProductDetailBinding
import com.example.bundlebundle.databinding.FragmentProductGridBinding
import com.example.bundlebundle.product.list.ProductGridFragment
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale
import kotlin.properties.Delegates

class ProductDetailFragment: Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val apiService = ApiClient.apiService
    private var productId by Delegates.notNull<Int>()

    private lateinit var openBottomSheetButton: Button
    private lateinit var productImageView: ImageView
    private lateinit var productNameTextView: TextView
    private lateinit var productPriceTextView: TextView
    private lateinit var productOriginTextView: TextView
    private lateinit var productPackageTypeTextView: TextView

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
        bottomSheetFragment.show(requireActivity().supportFragmentManager, "bottomSheet")
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