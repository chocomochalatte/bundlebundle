package com.example.bundlebundle.template

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bundlebundle.databinding.ActivityUpbuttonTemplateBinding

abstract class UpbuttonTemplateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpbuttonTemplateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpbuttonTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val fragment = setFragment()
        putFragment(fragment)
    }

    abstract fun setFragment(): Fragment

    protected fun putFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarTransparent.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
