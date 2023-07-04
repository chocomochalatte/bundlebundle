package com.example.bundlebundle.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bundlebundle.CartActivity
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentBottomSheetBinding
import com.example.bundlebundle.databinding.FragmentProductGridBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.properties.Delegates


open class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvQuantity: TextView
    private var quantity = 0
    private lateinit var intent: Intent

    private var selection by Delegates.notNull<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        selection = arguments?.getString(ARG_SELECTION) ?: "cart"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intent = requireActivity().intent

//        when (selection) {
//            "cart" -> {
//                var cartBtn = view.findViewById<AppCompatButton>(R.id.bottom_sheet_personal_cart_button)
//                cartBtn.text = "개인 장바구니"
//                cartBtn.setOnClickListener {
//                    val newIntent = Intent(context, CartActivity::class.java)
//                    newIntent.putExtra("tab", "personal")
//                    newIntent.putExtra("productId", intent.getIntExtra("productId", -1))
//                    newIntent.putExtra("productCnt", quantity)
//                    requireActivity().startActivity(newIntent)
//                }
//            }
//            "purchase" -> {
//                binding.bottomSheetLeftButton
//                var purchaseBtn = view.findViewById<AppCompatButton>(R.id.bottom_sheet_purchase_button)
//                purchaseBtn.text = "바로 구매하기"
//                purchaseBtn.setOnClickListener {
////                    val intent = Intent(context, PurchaseActivity::class.java)
////                    startActivity(intent)
//                }
//            }
//        }
//        var groupCartBtn = view.findViewById<AppCompatButton>(R.id.bottom_sheet_group_cart_button)
//        groupCartBtn.setOnClickListener {
//            val newIntent = Intent(context, CartActivity::class.java)
//            newIntent.putExtra("tab", "group")
//            newIntent.putExtra("productId", intent.getIntExtra("productId", -1))
//            newIntent.putExtra("productCnt", quantity)
//            requireActivity().startActivity(newIntent)
//        }

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

