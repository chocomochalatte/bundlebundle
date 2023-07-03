package com.example.bundlebundle.product.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bundlebundle.R
import com.example.bundlebundle.template.BaseTemplateActivity

class ProductPageActivity: BaseTemplateActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 아무 것도 안 클릭했을 때
        replaceFragment(ProductGridFragment.newInstance("discount"))

        binding.toolbarMain.saleButton.setOnClickListener {
            val fragment = ProductGridFragment.newInstance("discount")
            replaceFragment(fragment)
        }

        binding.toolbarMain.bestButton.setOnClickListener {
            val fragment = ProductGridFragment.newInstance("best")
            replaceFragment(fragment)
        }

        binding.toolbarMain.newButton.setOnClickListener {
            val fragment = ProductGridFragment.newInstance("newest")
            replaceFragment(fragment)
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                              .replace(R.id.main_content_fragment, fragment)
                              .commit()
    }
}
