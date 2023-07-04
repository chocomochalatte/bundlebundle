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
import com.example.bundlebundle.databinding.FragmentBottomSheetPurchaseBinding
import com.example.bundlebundle.databinding.FragmentProductGridBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.properties.Delegates


class BottomSheetPurchaseFragment : BottomSheetFragment() {

    private var _binding: FragmentBottomSheetPurchaseBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvQuantity: TextView
    private var quantity = 0
    private lateinit var intent: Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intent = requireActivity().intent
        tvQuantity = binding.tvQuantity

        binding.bottomSheetPurchaseButton.setOnClickListener {
            var purchaseBtn = view.findViewById<AppCompatButton>(R.id.bottom_sheet_purchase_button)
//            val newIntent = Intent(context, PurchaseActivity::class.java)
//            newIntent.putExtra("productId", intent.getIntExtra("productId"))
//            newIntent.putExtra("productCnt", tvQuantity)
//            startActivity(intent)
        }
    }
}

