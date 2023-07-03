package com.example.bundlebundle

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bundlebundle.group.GroupJoinFragment
import com.example.bundlebundle.template.UpbuttonTemplateActivity

class NewGroupCartActivity : UpbuttonTemplateActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setFragment(): Fragment {
        return GroupJoinFragment.newInstance("밍딩")
    }

}