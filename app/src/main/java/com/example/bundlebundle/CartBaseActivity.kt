package com.example.bundlebundle

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bundlebundle.group.GroupCreateFragment
import com.example.bundlebundle.template.SimpleTemplateActivity

class CartBaseActivity : SimpleTemplateActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbarSimple.simpleToolbarTitle.text = "장바구니"
    }

    override fun setFragment(): Fragment {
        return GroupCreateFragment.newInstance()
    }
}
