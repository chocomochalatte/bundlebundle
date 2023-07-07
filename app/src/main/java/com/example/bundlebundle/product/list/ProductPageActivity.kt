package com.example.bundlebundle.product.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.example.bundlebundle.R
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
        val showToast = intent.getBooleanExtra("showToast", false)
        if (showToast) {
            toast();
        }
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
                    intent.putExtra("groupId", 50 )
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
