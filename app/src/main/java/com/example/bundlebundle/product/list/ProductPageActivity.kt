package com.example.bundlebundle.product.list

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.example.bundlebundle.R
import com.example.bundlebundle.template.BaseTemplateActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.kakao.sdk.common.util.Utility


class ProductPageActivity: BaseTemplateActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var keyHash = Utility.getKeyHash(this)

        setTabAndViewPager()
        val showToast = intent.getBooleanExtra("showToast", false)
        if (showToast) {
            toast();
        }
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

    private fun toast() {
        var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_view_holder,null)
        var text : TextView = layoutInflater.findViewById(R.id.TextViewToast)
        text.text="로그인에 성공하였습니다"
        var toast = Toast(this)
        toast.view = layoutInflater
        //toast.setGravity(Gravity.TOP,0,400);
        toast.show()
    }

}
