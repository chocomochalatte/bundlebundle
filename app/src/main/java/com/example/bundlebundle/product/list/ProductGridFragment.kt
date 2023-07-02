package com.example.bundlebundle.product.list

import android.os.Bundle
import android.text.style.TtsSpan.ARG_QUERY_STRING
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.ActivityProductPageBinding
import com.example.bundlebundle.retrofit.ApiClient.apiService
import com.example.bundlebundle.retrofit.dataclass.ProductVO
import kotlinx.coroutines.runBlocking

/* 상품 목록을 표시하는 Fragment */
class ProductGridFragment : Fragment() {

    private var columnCount = 2
    private var sortType: String = "best"
    private var products: List<ProductVO> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            sortType = it.getString("sortType").toString()
        }

        getProductFromApi(sortType)

        bindProductData(products)
    }

    private fun bindProductData(productList: List<ProductVO>) {
        val recyclerView = view as? RecyclerView
        recyclerView?.adapter = ProductItemRecyclerViewAdapter(productList)
    }

    private fun getProductFromApi(sortType: String) {
        runBlocking {
            products = apiService.showProducts(sortType)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // XML 레이아웃 파일을 인플레이션하여 View 객체 생성
        val view = inflater.inflate(R.layout.fragment_product_grid_list, container, false)

        // View 객체가 RecyclerView인지 확인
        if (view is RecyclerView) {
            val layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)        // columnCount가 1 이하인 경우 -> 세로 방향으로 아이템들 일렬로 나열
                else -> GridLayoutManager(context, columnCount)         // columnCount가 2 이상인 경우 -> 그리드 형태로 아이템들 배치
            }
            // RecyclerView에 LayoutManager 설정
            view.layoutManager = layoutManager

            // RecyclerView에 어댑터 설정
            view.adapter = ProductItemRecyclerViewAdapter(products ?: emptyList())
        }
        return view
    }


    companion object {

        @JvmStatic
        fun newInstance(sortType: String): ProductGridFragment {
            return ProductGridFragment().apply {
                arguments = Bundle().apply {
                    putString("sortType", sortType)
                }
            }
        }

    }
}
