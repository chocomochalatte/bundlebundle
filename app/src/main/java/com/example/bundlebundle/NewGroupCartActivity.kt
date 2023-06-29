package com.example.bundlebundle

import android.os.Bundle
import androidx.fragment.app.Fragment

class NewGroupCartActivity : UpbuttonTemplateActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setFragment(): Fragment {
        return GroupJoinFragment.newInstance("밍딩")
    }

}