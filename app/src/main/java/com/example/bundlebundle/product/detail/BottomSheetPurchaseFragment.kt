package com.example.bundlebundle.product.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentBottomSheetPurchaseBinding

import java.text.NumberFormat
import java.util.Locale


class BottomSheetPurchaseFragment : BottomSheetFragment() {

    private var _binding: FragmentBottomSheetPurchaseBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvQuantity: TextView
    private var quantity = 0
    private lateinit var intent: Intent

    private var newProductCnt=0
    private var totalPrice=0

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

        // 마이너스 버튼 누른 경우
        binding.btnMinus.setOnClickListener{
            minusProductCnt()
        }

        // 플러스 버튼 누른 경우
        binding.btnPlus.setOnClickListener {
            plusProductCnt()
        }

        binding.bottomSheetPurchaseButton.setOnClickListener {
            var purchaseBtn = view.findViewById<AppCompatButton>(R.id.bottom_sheet_purchase_button)
//            val newIntent = Intent(context, PurchaseActivity::class.java)
//            newIntent.putExtra("productId", intent.getIntExtra("productId"))
//            newIntent.putExtra("productCnt", tvQuantity)
//            startActivity(intent)
        }
    }

    private fun plusProductCnt() {
        val productCnt = binding.tvQuantity.text.toString().replace(",", "").toInt()
        val price = binding.tvPrice.text.toString().replace(",", "").toInt()
        newProductCnt = productCnt + 1
        binding.tvQuantity.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(newProductCnt)
        totalPrice = newProductCnt * price
        binding.tvtotalPrice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice)
    }

    private fun minusProductCnt() {
        val productCnt = binding.tvQuantity.text.toString().replace(",", "").toInt()
        val price = binding.tvPrice.text.toString().replace(",", "").toInt()
        if (productCnt > 1) {
            newProductCnt = productCnt - 1
            binding.tvQuantity.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(newProductCnt)
            totalPrice = newProductCnt * price
            binding.tvtotalPrice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice)
        }
    }
}

