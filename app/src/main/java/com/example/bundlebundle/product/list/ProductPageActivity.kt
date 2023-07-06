package com.example.bundlebundle.product.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.example.bundlebundle.group.GroupActivity
import com.example.bundlebundle.template.BaseTemplateActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase


class ProductPageActivity: BaseTemplateActivity() {

    val TAG = "ProductPageActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTabAndViewPager()
        handleDynamicLinks()
    }

    private fun handleDynamicLinks(){
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                var deeplink: Uri? = null
                if(pendingDynamicLinkData != null) {
                    deeplink = pendingDynamicLinkData.link
                }

                if(deeplink != null) {
                    val intent = Intent(this, GroupActivity::class.java)
                    intent.putExtra("pageType", "join")
                    intent.putExtra("groupId", 4)
                    intent.putExtra("groupOwnerNickname", "ming")
                    startActivity(intent)
                }
                else {
                    Log.d(TAG, "getDynamicLink: no link found")
                }
            }
            .addOnFailureListener(this) { e -> Log.e(TAG, "getDynamicLink:onFailure", e) }
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
