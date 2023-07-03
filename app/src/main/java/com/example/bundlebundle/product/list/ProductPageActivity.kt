package com.example.bundlebundle.product.list

import android.os.Bundle
import com.example.bundlebundle.template.BaseTemplateActivity
import com.google.android.material.tabs.TabLayoutMediator

class ProductPageActivity: BaseTemplateActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTabAndViewPager()
    }

    private fun setTabAndViewPager() {
        val tabAdapter = MenuTabAdapter(this)
        tabAdapter.addMenuTab(ProductGridFragment.newInstance("best"), "Home")
        tabAdapter.addMenuTab(ProductGridFragment.newInstance("best"), "Best")
        tabAdapter.addMenuTab(ProductGridFragment.newInstance("newest"), "New")
        tabAdapter.addMenuTab(ProductGridFragment.newInstance("discount"), "Sale")

        val menuViewPager2 = binding.toolbarMain.menuViewPager
        menuViewPager2.adapter = tabAdapter

        val tabLayout = binding.toolbarMain.menuTabLayout
        TabLayoutMediator(tabLayout, menuViewPager2) { tab, position ->
            tab.text = tabAdapter.getTabTitle(position)
        }.attach()
    }

}
