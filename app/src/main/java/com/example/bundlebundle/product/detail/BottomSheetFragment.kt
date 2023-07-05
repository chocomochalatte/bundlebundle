package com.example.bundlebundle.product.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentBottomSheetBinding
import com.example.bundlebundle.retrofit.dataclass.product.ProductVO
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.properties.Delegates


open class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvQuantity: TextView
    private var quantity = 0
    private lateinit var intent: Intent

    private lateinit var productInfo: ProductVO

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
        internal const val PRODUCT_INFO = "productVO"

        fun newInstance(selection: String, productInfo: ProductVO): Fragment {
            val fragment = BottomSheetFragment()
            val args = Bundle().apply {
                putString(ARG_SELECTION, selection)
                putParcelable(PRODUCT_INFO, productInfo)
            }
            fragment.arguments = args
            return fragment
        }
    }
}

