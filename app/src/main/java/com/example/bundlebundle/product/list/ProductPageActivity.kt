package com.example.bundlebundle.product.list

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.example.bundlebundle.template.BaseTemplateActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.kakao.sdk.common.util.Utility
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class ProductPageActivity: BaseTemplateActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var keyHash = Utility.getKeyHash(this)
        Log.d("kako", keyHash)

        setTabAndViewPager()
    }

    private fun setTabAndViewPager() {
        val tabAdapter = MenuTabAdapter(this)
        tabAdapter.addMenuTab(ProductGridFragment.newInstance("home"), "Home")
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
