package com.example.bundlebundle.product.list

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.ActivityProductPageBinding
import com.example.bundlebundle.template.BaseTemplateActivity

class ProductListActivity : BaseTemplateActivity(), View.OnClickListener {
    private lateinit var saleButton: Button
    private lateinit var bestButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityProductPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        saleButton = findViewById(R.id.saleButton)
        bestButton = findViewById(R.id.bestButton)

        saleButton.setOnClickListener(this)
        bestButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.saleButton -> {
                // "discount"를 sortType으로 설정하여 요청을 보냄
                val fragment = ProductGridFragment.newInstance("discount")
                replaceFragment(fragment)
            }
            R.id.bestButton -> {
                // "best"를 sortType으로 설정하여 요청을 보냄
                val fragment = ProductGridFragment.newInstance("best")
                replaceFragment(fragment)
            }
            // handle other button clicks if needed
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
