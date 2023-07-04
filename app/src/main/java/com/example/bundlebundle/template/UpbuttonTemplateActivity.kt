package com.example.bundlebundle.template

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // 뒤로가기 버튼 클릭 시 수행할 동작
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
