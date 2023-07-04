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
import com.example.bundlebundle.databinding.FragmentBottomSheetCartBinding
import com.example.bundlebundle.databinding.FragmentProductGridBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.properties.Delegates


class BottomSheetCartFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvQuantity: TextView
    private var quantity = 1
    private lateinit var intent: Intent

    private var selection by Delegates.notNull<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intent = requireActivity().intent
        tvQuantity = view.findViewById(R.id.tvQuantity)

        binding.bottomSheetPersonalCartButton.setOnClickListener {
            val newIntent = Intent(context, CartActivity::class.java)
            newIntent.putExtra("tab", "personal")
            newIntent.putExtra("productId", intent.getIntExtra("productId", -1))
            newIntent.putExtra("productCnt", quantity)
            requireActivity().startActivity(newIntent)
        }

        binding.bottomSheetGroupCartButton.setOnClickListener {
            val newIntent = Intent(context, CartActivity::class.java)
            newIntent.putExtra("tab", "group")
            newIntent.putExtra("productId", intent.getIntExtra("productId", -1))
            newIntent.putExtra("productCnt", quantity)
            requireActivity().startActivity(newIntent)
        }
    }
}

