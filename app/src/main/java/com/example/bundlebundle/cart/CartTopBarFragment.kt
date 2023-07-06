package com.example.bundlebundle.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundlebundle.databinding.FragmentCartTopbarBinding
import com.example.bundlebundle.retrofit.dataclass.cart.CartVO


class CartTopBarFragment : Fragment() {
    private var _binding: FragmentCartTopbarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartTopbarBinding.inflate(inflater, container, false)
        arguments?.let {
            val myData = it.getParcelable<CartVO>(MY_CART_TOTAL_CNT)
            val cartCnt = myData?.cartCnt.toString()
            binding.mycartitemTotalcnt.text = cartCnt
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun newInstance(myData: CartVO): CartTopBarFragment {
        var fragment = CartTopBarFragment()
        val args = Bundle().apply {
            putParcelable(MY_CART_TOTAL_CNT,myData)
        }
        fragment.arguments = args
        return fragment
    }


    companion object {
        fun newInstance(data: CartVO): Fragment {
            var fragment = CartTopBarFragment()
            val args = Bundle().apply {
                putParcelable(MY_CART_TOTAL_CNT,data)
            }
            fragment.arguments = args
            return fragment
        }

        private const val MY_CART_TOTAL_CNT = "argMyData"
    }
}