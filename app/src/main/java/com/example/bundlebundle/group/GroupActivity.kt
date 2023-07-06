package com.example.bundlebundle.group

import androidx.fragment.app.Fragment
import com.example.bundlebundle.template.UpbuttonTemplateActivity

class GroupActivity() : UpbuttonTemplateActivity() {

    private lateinit var pageType: String


    override fun setFragment(): Fragment {

        pageType = intent.getStringExtra("pageType").toString()
        return when (pageType) {
            "create" -> GroupCreateFragment.newInstance()
            "join" -> GroupJoinFragment.newInstance(intent.getStringExtra("groupOwnerNickname")!!)
            else -> GroupCreateFragment.newInstance()
        }
    }
}