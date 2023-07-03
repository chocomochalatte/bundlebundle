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
import androidx.lifecycle.lifecycleScope
import com.example.bundlebundle.R
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

private var columnCount = 2
    private lateinit var sortType: String
    private lateinit var products: List<ProductVO>
    private var apiService = ApiClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sortType = arguments?.getString(ARG_SORT_TYPE, "best") ?: "best"
        Log.d("ming", "-----------sortType=$sortType")

        lifecycleScope.launch {
            products = getProductFromApi(sortType)?: emptyList()
            bindProductData(products)
            Log.d("ming", "-----------products=$products")
        }

    }

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

    private fun bindProductData(products: List<ProductVO>) {
        val recyclerView = view as? RecyclerView
        recyclerView?.adapter = ProductGridAdapter(products)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_grid, container, false)

        if (view is RecyclerView) {
            val layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            view.layoutManager = layoutManager
            view.adapter = ProductGridAdapter(products)
        }

        return view
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
