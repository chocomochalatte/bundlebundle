package com.example.bundlebundle.product.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MenuTabAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    private val menuFragmentList: MutableList<Fragment> = ArrayList()
    private val menuTitleList: MutableList<String> = ArrayList()

    fun getTabTitle(position : Int): String{
        return menuTitleList[position]
    }

    fun addMenuTab(fragment: Fragment, title: String) {
        menuFragmentList.add(fragment)
        menuTitleList.add(title)
    }

    override fun getItemCount() = menuFragmentList.size

    override fun createFragment(position: Int): Fragment {
        return menuFragmentList[position]
    }
}