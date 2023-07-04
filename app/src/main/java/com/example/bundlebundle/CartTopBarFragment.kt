package com.example.bundlebundle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundlebundle.SharedMyCartItem.myData
import com.example.bundlebundle.databinding.FragmentCartTopbarBinding
import com.example.bundlebundle.retrofit.dataclass.CartVO
import com.example.bundlebundle.retrofit.dataclass.GroupCartListVO


class CartTopBarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCartTopbarBinding.inflate(layoutInflater)
        arguments?.let {
            val myData = it.getParcelable<CartVO>(MY_CART_TOTAL_CNT)
            val cartCnt = myData?.cartCnt.toString()
            binding.mycartitemTotalcnt.text= cartCnt
        }
        return binding.root
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