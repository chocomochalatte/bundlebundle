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
        products.add(Product(R.drawable.cherry, "[생생] 생체리", "18,400원","23,300"))
        products.add(Product(R.drawable.egg, "[자취네] 계란", "18,400원","23,300"))
        products.add(Product(R.drawable.bread, "[우리밀] 페스츄리", "18,400원","23,300"))
        products.add(Product(R.drawable.bread2, "[우리밀] 식빵", "18,400원","23,300"))
        products.add(Product(R.drawable.banana, "[남양] 바나나", "18,400원","23,300"))
        return products
    }
}
