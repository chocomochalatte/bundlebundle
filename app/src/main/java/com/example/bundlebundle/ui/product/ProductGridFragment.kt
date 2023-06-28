package com.example.bundlebundle.ui.product

import ProductItemRecyclerViewAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundlebundle.R
import com.example.bundlebundle.placeholder.PlaceholderContent

/* 상품 목록을 표시하는 Fragment */
class ProductGridFragment : Fragment() {

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
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
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)        // columnCount가 1 이하인 경우 -> 세로 방향으로 아이템들 일렬로 나열
                    else -> GridLayoutManager(context, columnCount)         // columnCount가 2 이상인 경우 -> 그리드 형태로 아이템들 배치
                }
                // RecyclerView에 어댑터 설정
                adapter = ProductItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }

    companion object {

        // 컬럼 수 설정
        const val ARG_COLUMN_COUNT = "column-count"

        /**
         * ProductGridFragment의 인스턴스를 생성하는 메서드
         *
         * @param columnCount 목록의 열 수
         * @return [ProductGridFragment] 인스턴스 */
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ProductGridFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}