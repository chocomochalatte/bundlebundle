package com.example.bundlebundle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var openBottomSheetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        openBottomSheetButton = findViewById(R.id.button_open_bottom_sheet)
        openBottomSheetButton.setOnClickListener {
            openBottomSheet()
        }
    }

    private fun openBottomSheet() {
        val bottomSheetFragment = BottomSheetPurchaseFragment()
        bottomSheetFragment.show(supportFragmentManager, "bottomSheet")
    }
}
