package com.example.bundlebundle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.bundlebundle.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCartBinding.inflate(layoutInflater)
        val myJangButton = binding.jangbaguniToolbar.myJangbaguni
        val grourJangButton = binding.jangbaguniToolbar.groupJangbaguni
        val backButton = binding.jangbaguniToolbar.jangBack

        var fragmentManager: FragmentManager = supportFragmentManager

        myJangButton.isSelected = true
        myJangButton.setBackgroundResource(R.drawable.buttonunderline)
        myJangButton.setOnClickListener {
            myJangButton.setTextColor(ContextCompat.getColor(this, R.color.orange))
            grourJangButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            myJangButton.setBackgroundResource(R.drawable.buttonunderline)
            grourJangButton.background = null
            //Fragment추가하는 부분 (이 부분은 장바구니 수량이 없는경우 조건을 걸어줘야함)
            var transaction = fragmentManager.beginTransaction()
            //var fragment = EmptyCartFragment()
            var fragment = CartFragment()
            transaction.add(R.id.noMyJangfragment,fragment)

            var fragment2 = CartTopBarFragment();
            transaction.add(R.id.recyclercart_item1,fragment2)

            var fragment1 = CartItemFragment()
            transaction.add(R.id.recyclercart_item1,fragment1)
            transaction.commit()
        }

        grourJangButton.setOnClickListener {
            grourJangButton.setTextColor(ContextCompat.getColor(this, R.color.orange))
            myJangButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            grourJangButton.setBackgroundResource(R.drawable.buttonunderline)
            myJangButton.background = null
            //Fragment추가하는 부분 (이 부분은 장바구니 수량이 없는경우 조건을 걸어줘야함)
            var transaction = fragmentManager.beginTransaction()

            var fragment = EmptyGroupCartFragment()
            transaction.replace(R.id.noMyJangfragment,fragment)
            transaction.commit()
        }

        backButton.setOnClickListener{
            onBackPressed()
        }

        setContentView(binding.root)
    }

}