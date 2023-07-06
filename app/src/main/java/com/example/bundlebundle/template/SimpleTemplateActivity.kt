package com.example.bundlebundle.template

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.bundlebundle.databinding.ActivitySimpleTemplateBinding
import com.example.bundlebundle.product.list.ProductPageActivity

abstract class SimpleTemplateActivity : AppCompatActivity() {
    lateinit var binding: ActivitySimpleTemplateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val fragment = setFragment()
        putFragment(fragment)

        binding.toolbarSimple.simpleToolbarTitle.text = setTitle()

        binding.toolbarSimple.homeBtn.setOnClickListener {
            val intent = Intent(this, ProductPageActivity::class.java)
            startActivity(intent)
        }
    }

    protected abstract fun setFragment(): Fragment

    protected abstract fun setTitle(): String

    protected fun putFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerSimple.id, fragment)
            .commit()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarSimple.toolbar)
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
