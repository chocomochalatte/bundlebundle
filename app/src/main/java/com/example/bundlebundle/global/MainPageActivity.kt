package com.example.bundlebundle.global

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.ActivityMainPageBinding
import com.example.bundlebundle.product.slider.ProductSliderFragment
import com.example.bundlebundle.product.slider.ViewPagerFragment


class MainPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainPageBinding.inflate(layoutInflater)

        // 뒤로가기 툴바를 위한 예시코드
        val next = binding.maintoolbar.shopImage

        next.setOnClickListener {
            var intent = Intent(this, MainDetailPageActivity::class.java)
            startActivity(intent)
        }

        // 장바구니 아이템 개수 설정
        binding.maintoolbar.cartCount.text = "5"
        // 장바구니 아이템 가시성 설정
        binding.maintoolbar.cartCount.visibility = View.VISIBLE // 보이도록 설정
        val homeButton = binding.maintoolbar.homeButton
        val premiumButton = binding.maintoolbar.premiumButton
        val bestButton =  binding.maintoolbar.bestButton
        val saleButton = binding.maintoolbar.saleButton

        homeButton.isSelected = true // Home 버튼을 클릭된 상태로 설정
        homeButton.setBackgroundResource(R.drawable.buttonunderline)
        homeButton.setOnClickListener {
            // Home 버튼 클릭 시
            homeButton.setTextColor(ContextCompat.getColor(this, R.color.orange))
            premiumButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            bestButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            saleButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            homeButton.setBackgroundResource(R.drawable.buttonunderline)
            premiumButton.background = null
            bestButton.background = null
            saleButton.background = null
        }

        premiumButton.setOnClickListener {
            // Premium 버튼 클릭 시
            homeButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            premiumButton.setTextColor(ContextCompat.getColor(this, R.color.orange))
            bestButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            saleButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            premiumButton.setBackgroundResource(R.drawable.buttonunderline)
            homeButton.background = null
            bestButton.background = null
            saleButton.background = null
        }

        bestButton.setOnClickListener {
            // Best 버튼 클릭 시
            homeButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            premiumButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            bestButton.setTextColor(ContextCompat.getColor(this, R.color.orange))
            saleButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            bestButton.setBackgroundResource(R.drawable.buttonunderline)
            homeButton.background = null
            premiumButton.background = null
            saleButton.background = null
        }

        saleButton.setOnClickListener {
            // Sale 버튼 클릭 시
            homeButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            premiumButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            bestButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            saleButton.setTextColor(ContextCompat.getColor(this, R.color.orange))
            saleButton.setBackgroundResource(R.drawable.buttonunderline)
            homeButton.background = null
            premiumButton.background = null
            bestButton.background = null
        }
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container1_main_page, ViewPagerFragment())
            .add(R.id.fragment_container2_main_page, ProductSliderFragment())
            .commit()

    }
}