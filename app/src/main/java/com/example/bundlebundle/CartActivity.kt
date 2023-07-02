package com.example.bundlebundle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.bundlebundle.databinding.ActivityCartBinding


class CartActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var myJangButton: TextView
    private lateinit var grourJangButton: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCartBinding.inflate(layoutInflater)
        myJangButton = binding.jangbaguniToolbar.myJangbaguni
        grourJangButton = binding.jangbaguniToolbar.groupJangbaguni
        val backButton = binding.jangbaguniToolbar.jangBack

        fragmentManager = supportFragmentManager

        myJangButton.isSelected = true
        myJangButton.setBackgroundResource(R.drawable.buttonunderline)

        myJangButton.setOnClickListener {
            myJangButton.setTextColor(ContextCompat.getColor(this, R.color.orange))
            grourJangButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            myJangButton.setBackgroundResource(R.drawable.buttonunderline)
            grourJangButton.background = null
            showMyJangFragments()
        }

        grourJangButton.setOnClickListener {
            grourJangButton.setTextColor(ContextCompat.getColor(this, R.color.orange))
            myJangButton.setTextColor(ContextCompat.getColor(this, R.color.middle_gray))
            grourJangButton.setBackgroundResource(R.drawable.buttonunderline)
            myJangButton.background = null
            showGroupJangFragments()
        }

        backButton.setOnClickListener{
            onBackPressed()
        }

        // 초기 Fragment 추가
        showMyJangFragments()

        setContentView(binding.root)
    }

    private fun showMyJangFragments() {
        //Fragment추가하는 부분 (이 부분은 장바구니 수량이 없는경우 조건을 걸어줘야함)
        var transaction = fragmentManager.beginTransaction()
        //var fragment = EmptyCartFragment()

        var fragment = CartTopBarFragment()
        transaction.replace(R.id.noMyJangfragment,fragment)

        var fragment1 = CartItemFragment()
        transaction.replace(R.id.item_cartfragment,fragment1)

        var fragment2 = CartBottomFragment()
        transaction.replace(R.id.bottom_cartfragment,fragment2)
        transaction.commit()
    }

    private fun showGroupJangFragments() {
        val transaction = fragmentManager.beginTransaction()

        val fragment = GroupCartTopBarFragment()
        transaction.replace(R.id.noMyJangfragment, fragment)

        val fragment1 = GroupCartItemFragment()
        transaction.replace(R.id.item_cartfragment, fragment1)

        val fragment2 = CartBottomFragment()
        transaction.replace(R.id.bottom_cartfragment, fragment2)

        transaction.commit()
    }

}