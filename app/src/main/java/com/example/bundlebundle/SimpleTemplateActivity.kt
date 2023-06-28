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

        val fragment = createFragment() // 추상 메서드 호출
        setFragment(fragment)
    }

    protected abstract fun createFragment(): Fragment // 추상 메서드

    protected fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarSimple.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
