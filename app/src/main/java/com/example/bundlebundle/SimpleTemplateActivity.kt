package com.example.bundlebundle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bundlebundle.databinding.ActivitySimpleTemplateBinding

abstract class SimpleTemplateActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimpleTemplateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val fragment = setFragment()
        putFragment(fragment)
    }

    protected abstract fun setFragment(): Fragment

    protected fun putFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarSimple.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
