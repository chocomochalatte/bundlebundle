package com.example.bundlebundle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetPurchaseFragment : BottomSheetDialogFragment() {
    private lateinit var tvQuantity: TextView
    private var quantity = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet_purchase, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvQuantity = view.findViewById(R.id.tvQuantity)

        val btnMinus = view.findViewById<Button>(R.id.btnMinus)
        btnMinus.setOnClickListener {
            decrementQuantity()
        }

        val btnPlus = view.findViewById<Button>(R.id.btnPlus)
        btnPlus.setOnClickListener {
            incrementQuantity()
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun decrementQuantity() {
        if (quantity > 0) {
            quantity--
            tvQuantity.text = quantity.toString()
        }
    }

    private fun incrementQuantity() {
        quantity++
        tvQuantity.text = quantity.toString()
    }
}

