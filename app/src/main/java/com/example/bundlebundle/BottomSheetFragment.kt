package com.example.bundlebundle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.bundlebundle.product.detail.ProductDetailFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.properties.Delegates


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var tvQuantity: TextView
    private var quantity = 0

    private var selection by Delegates.notNull<String>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        selection = arguments?.getString(BottomSheetFragment.ARG_SELECTION) ?: "cart"

        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        when (selection) {
            "cart" -> view.findViewById<AppCompatButton>(R.id.bottom_sheet_right_button).text = "개인 장바구니"
            "purchase" -> view.findViewById<AppCompatButton>(R.id.bottom_sheet_right_button).text = "바로 구매하기"
        }

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

    companion object {
        private const val ARG_SELECTION = "selection"

        @JvmStatic
        fun newInstance(selection: String): BottomSheetFragment {
            return BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SELECTION, selection)
                }
            }
        }
    }
}

