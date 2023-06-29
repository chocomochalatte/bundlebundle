package com.example.bundlebundle

import android.os.Bundle
import androidx.fragment.app.Fragment

class NewGroupCartActivity : UpbuttonTemplateActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 내부 fragment 생성 및 설정
        val fragment = GroupJoinFragment.newInstance("밍딩")
        setFragment(fragment)
    }

    override fun createFragment(): Fragment {
        return ProductFragment()
    }
}