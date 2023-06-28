package com.example.bundlebundle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val products = getProducts() // 상품 목록을 가져옴
        productAdapter = ProductAdapter(products)
        recyclerView.adapter = productAdapter

        return view
    }

    private fun getProducts(): List<Product> {
        // 상품 목록을 생성하고 반환
        // 예시:
        val products = mutableListOf<Product>()
        products.add(Product(R.drawable.cherry, "상품 1", "상품 설명 1"))
        products.add(Product(R.drawable.egg, "상품 2", "상품 설명 2"))
        products.add(Product(R.drawable.bread, "상품 3", "상품 설명 3"))
        products.add(Product(R.drawable.bread2, "상품 4", "상품 설명 4"))
        products.add(Product(R.drawable.banana, "상품 5", "상품 설명 5"))
        return products
    }
}
