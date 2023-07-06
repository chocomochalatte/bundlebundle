package com.example.bundlebundle.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundlebundle.databinding.FragmentGroupCartTopBarBinding
import com.example.bundlebundle.retrofit.dataclass.cart.GroupCartListVO

class GroupCartTopBarFragment : Fragment() {
    private lateinit var binding: FragmentGroupCartTopBarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupCartTopBarBinding.inflate(layoutInflater)
        arguments?.let {
            val groupData = it.getParcelable<GroupCartListVO>(GROUP_CART_TOTAL_CNT)
            val groupCartCnt = groupData?.totalCnt.toString()
            binding.groupcartitemCnt.text = groupCartCnt

        }
        return binding.root
    }

    fun newInstance(groupData: GroupCartListVO): GroupCartTopBarFragment {
        val fragment = GroupCartTopBarFragment()
        val args = Bundle().apply {
            putParcelable(GROUP_CART_TOTAL_CNT, groupData)
        }
        fragment.arguments = args
        return fragment
    }

    companion object {
        private const val GROUP_CART_TOTAL_CNT = "groupCartItem"

        fun newInstance(groupData: GroupCartListVO): GroupCartTopBarFragment {
            val fragment = GroupCartTopBarFragment()
            val args = Bundle().apply {
                putParcelable(GROUP_CART_TOTAL_CNT, groupData)
            }
            fragment.arguments = args
            return fragment
        }
    }



}

