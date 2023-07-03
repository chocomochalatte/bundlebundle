package com.example.bundlebundle.product.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bundlebundle.R
import com.example.bundlebundle.template.BaseTemplateActivity

class ProductPageActivity: BaseTemplateActivity() {

    override fun setTopLevelMainFragment(): Set<Int> {
        return setOf(R.id.main_content_fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbarMain.saleButton.setOnClickListener {
            val fragment = ProductGridFragment.newInstance("discount")
            navigateToFragment(fragment)
        }

        binding.toolbarMain.bestButton.setOnClickListener {
            val fragment = ProductGridFragment.newInstance("best")
            navigateToFragment(fragment)
        }

        binding.toolbarMain.newButton.setOnClickListener {
            val fragment = ProductGridFragment.newInstance("newest")
            navigateToFragment(fragment)
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content_fragment_container, fragment)
            .commit()
    }
}
