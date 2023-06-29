package com.example.bundlebundle

import android.os.Bundle
import androidx.fragment.app.Fragment

class CartBaseActivity : SimpleTemplateActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbarSimple.simpleToolbarTitle.text = "장바구니"

        // 내부 fragment 생성 및 설정
//        val fragment = GroupJoinFragment.newInstance("밍딩")
        val fragment = GroupCreateFragment.newInstance()
        setFragment(fragment)
    }

    override fun createFragment(): Fragment {
        return ProductFragment()
    }
}
