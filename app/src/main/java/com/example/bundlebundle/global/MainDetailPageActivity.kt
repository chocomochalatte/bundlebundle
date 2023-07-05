package com.example.bundlebundle.global

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.bundlebundle.databinding.ActivityMainDetailPageBinding


class MainDetailPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainDetailPageBinding.inflate(layoutInflater)
        val toolbar: Toolbar = binding.maindetailtoolbar.detailToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // 뒤로가기 버튼이 클릭되면 기본 뒤로가기 동작 수행
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}