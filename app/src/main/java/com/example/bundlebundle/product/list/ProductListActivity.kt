package com.example.bundlebundle.product.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.bundlebundle.R
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.template.BaseTemplateActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListActivity : BaseTemplateActivity(), View.OnClickListener {
    private lateinit var saleButton: Button
    private lateinit var bestButton: Button

    private val apiService = ApiClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ...

        saleButton = findViewById(R.id.saleButton)
        bestButton = findViewById(R.id.bestButton)

        saleButton.setOnClickListener(this)
        bestButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.saleButton -> {
                // "discount"를 sortType으로 설정하여 요청을 보냄
                fetchProducts("discount")
            }
            R.id.bestButton -> {
                // "best"를 sortType으로 설정하여 요청을 보냄
                fetchProducts("best")
            }
            // handle other button clicks if needed
        }
    }

    private fun fetchProducts(sortType: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                // 비동기로 Retrofit을 사용하여 데이터를 가져옴
                val productList = withContext(Dispatchers.IO) {
                    apiService.showProducts(sortType)
                }
                // ProductGridFragment에 데이터 전달
                val fragment = ProductGridFragment.newInstance(sortType)
                fragment.setProductList(productList)
                replaceFragment(fragment)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun setTopLevelMainFragment(): Set<Int> {
        return setOf(R.id.product_list)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.product_list, fragment)
            .commit()
    }
}
