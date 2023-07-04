package com.example.bundlebundle.group

import androidx.fragment.app.Fragment
import com.example.bundlebundle.template.UpbuttonTemplateActivity

class GroupActivity(fragment: Fragment) : UpbuttonTemplateActivity() {
    private val fragment = fragment

    override fun setFragment(): Fragment {
        return fragment
    }
}