package com.example.bundlebundle.product.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentProductGridBinding
import com.example.bundlebundle.product.slider.ProductSliderFragment
import com.example.bundlebundle.product.slider.ViewPagerFragment
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/* 상품 목록을 표시하는 Fragment */
class ProductGridFragment : Fragment() {

    private var _binding: FragmentProductGridBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 2
    private lateinit var sortType: String
    private lateinit var products: List<ProductVO>

    private val productApiService = ApiClient.productApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductGridBinding.inflate(inflater, container, false)

        bindWithApiResponse()
        binding.productGridList.layoutManager = GridLayoutManager(requireContext(), columnCount)
        return binding.root
    }

    private fun bindWithApiResponse() {
        sortType = arguments?.getString(ARG_SORT_TYPE) ?: "best"

        if (sortType.equals("home") && binding.fragmentContainer1.isEmpty()) {
            requireActivity().supportFragmentManager.beginTransaction()
                                                    .add(R.id.fragment_container1, ViewPagerFragment())
                                                    .add(R.id.fragment_container2, ProductSliderFragment())
                                                    .commit()
            binding.advTextMain.text = "7월, 제철에 만나는\n신선한 과일"
            binding.advTextSub.text = "산지 직송 및 예약 판매"
            binding.productSortingSpinner.isVisible = false
            binding.productGridList.isVisible = false

//            binding.advText.setTextSize(25F)
        }

        lifecycleScope.launch {
            products = getProductFromApi(sortType) ?: emptyList()
            bindProductData(products)
        }
    }

    private suspend fun getProductFromApi(sortType: String): List<ProductVO>? {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<List<ProductVO>?> { continuation ->
                val call = productApiService.showProducts(sortType)
                call.enqueue(object : Callback<List<ProductVO>> {
                    override fun onResponse(call: Call<List<ProductVO>>, response: Response<List<ProductVO>>) {
                        if (response.isSuccessful) {
                            val products = response.body()
                            continuation.resume(products)
                        } else {
                            continuation.resume(null)
                        }
                    }

                    override fun onFailure(call: Call<List<ProductVO>>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
            }
        }
    }

    private fun bindProductData(products: List<ProductVO>) {
        binding.productGridList.adapter = ProductGridAdapter(products)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_SORT_TYPE = "sortType"

        @JvmStatic
        fun newInstance(sortType: String): ProductGridFragment {
            return ProductGridFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SORT_TYPE, sortType)
                }
            }
        }
    }
}