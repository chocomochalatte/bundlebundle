package com.example.bundlebundle.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bundlebundle.CartBottomFragment
import com.example.bundlebundle.CartItemFragment
import com.example.bundlebundle.CartTopBarFragment
import com.example.bundlebundle.GroupCartItemFragment
import com.example.bundlebundle.GroupCartTopBarFragment
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentCartContentBinding
import com.google.android.material.tabs.TabLayout

class CartContentFragment : Fragment() {
    private var _binding: FragmentCartContentBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartTab: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartTab = binding.tabLayout
        setTab(cartTab)

        val fragmentManager: FragmentManager = childFragmentManager
        setTabListeners(fragmentManager)

        val startingTab: String = requireActivity().intent.getStringExtra("tab") ?: "personal"
        setStartingTab(startingTab)
    }

    private fun setTab(cartTab: TabLayout) {
        val tabPersonalCart = cartTab.newTab()
        tabPersonalCart.text = "개인 장바구니"
        val tabGroupCart = cartTab.newTab()
        tabGroupCart.text = "그룹 장바구니"

        cartTab.addTab(tabPersonalCart)
        cartTab.addTab(tabGroupCart)
    }

    private fun setStartingTab(startingTab: String) {
        val tabIndex = when (startingTab) {
            "group" -> 1
            "personal" -> 0
            else -> 0
        }
        cartTab.getTabAt(tabIndex)?.select()
    }


    private fun setTabListeners(fragmentManager: FragmentManager) {
        cartTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val transaction = fragmentManager.beginTransaction()
                val fragment = when (tab.position) {
                    0 -> CartTopBarFragment()
                    else -> GroupCartTopBarFragment()
                }
                transaction.replace(R.id.noMyJangfragment, fragment)

                val fragment1 = when (tab.position) {
                    0 -> CartItemFragment()
                    else -> GroupCartItemFragment()
                }
                transaction.replace(R.id.item_cartfragment, fragment1)

                transaction.replace(R.id.bottom_cartfragment, CartBottomFragment())

                transaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

}