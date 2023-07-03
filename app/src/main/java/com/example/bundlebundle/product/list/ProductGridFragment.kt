package com.example.bundlebundle.product.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentProductGridBinding
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import kotlin.coroutines.resumeWithException
import com.example.bundlebundle.retrofit.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/* 상품 목록을 표시하는 Fragment */
class ProductGridFragment : Fragment() {
    private var _binding: FragmentProductGridBinding? = null
    val binding get() = _binding!!

    private var columnCount = 2
    private lateinit var sortType: String
    private lateinit var products: List<ProductVO>
    private var apiService = ApiClient.apiService

    private suspend fun getProductFromApi(sortType: String): List<ProductVO> {
        return withContext(Dispatchers.IO) {
            return@withContext suspendCoroutine<List<ProductVO>> { continuation ->
                val call = apiService.showProducts(sortType)
                call.enqueue(object : Callback<List<ProductVO>> {
                    override fun onResponse(call: Call<List<ProductVO>>, response: Response<List<ProductVO>>) {
                        if (response.isSuccessful) {
                            val products = response.body() ?: emptyList()
                            continuation.resume(products)
                        } else {
                            continuation.resume(emptyList())
                        }
                    }

                    override fun onFailure(call: Call<List<ProductVO>>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
            }
        }
    }

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

        lifecycleScope.launch {
            products = getProductFromApi(sortType)?: emptyList()
            bindProductData(products)
        }
    }
    private fun bindProductData(products: List<ProductVO>) {
        binding.productGridList.adapter = ProductGridAdapter(products)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
