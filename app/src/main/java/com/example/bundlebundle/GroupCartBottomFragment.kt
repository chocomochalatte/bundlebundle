package com.example.bundlebundle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundlebundle.SharedMyCartItem.myData
import com.example.bundlebundle.databinding.FragmentGroupCartBottomBinding
import com.example.bundlebundle.retrofit.dataclass.CartVO
import com.example.bundlebundle.retrofit.dataclass.GroupCartListVO
import java.text.NumberFormat
import java.util.Locale


class GroupCartBottomFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGroupCartBottomBinding.inflate(layoutInflater)
        arguments?.let {
            val groupData = it.getParcelable<GroupCartListVO>(GROUP_CART_BOTTOM)

            val groupProductCount = groupData?.totalCnt
            var grouptotalprice:Int = 0
            var groupdiscountprice:Int = 0
            var groupshipmentprice:Int = 0
            var groupresultprice:Int = 0
            var grouporderCnt:Int = 0

            for (i in 0 until groupProductCount!!) {
                val size = groupData.groupCart[i].cartCnt
                grouporderCnt += groupData.groupCart[i].cartCnt
                for (j in 0 until size) {
                    val currentitem = groupData.groupCart[i].cartProducts
                    grouptotalprice += currentitem[j].productPrice * currentitem[j].productCnt
                    val discountRate = (currentitem[j]?.discountRate ?: 0) / 100.0 // 비율로 변환
                    val discount = (discountRate * (currentitem[j]?.productPrice ?: 0)).toInt()
                    groupdiscountprice += discount * (currentitem[j]?.productCnt ?: 0)
                }
            }

            if(grouptotalprice-groupdiscountprice>=50000){
                groupshipmentprice = 0
            }else{
                groupshipmentprice = 3500
            }
            groupresultprice = grouptotalprice-groupdiscountprice-groupshipmentprice

            val totalpriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(grouptotalprice)
            binding.groupcartitemTotalprice.text = totalpriceFormatted

            val discountpriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(groupdiscountprice)
            binding.groupcartitemDiscountprice.text = discountpriceFormatted

            val shipmentpriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(groupshipmentprice)
            binding.groupcartitemShipprice.text = shipmentpriceFormatted

            val resultpriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(groupresultprice)
            binding.groupcartitemResultprice.text = resultpriceFormatted

            binding.groupcartOrderCnt.text = grouporderCnt.toString()

        }
        return binding.root
    }

    fun newInstance(groupData: GroupCartListVO): GroupCartBottomFragment {
        var fragment = GroupCartBottomFragment()
        val args = Bundle().apply {
            putParcelable(GROUP_CART_BOTTOM,groupData)
        }
        fragment.arguments = args
        return fragment
    }

    companion object {
        fun newInstance(groupData: GroupCartListVO): GroupCartBottomFragment {
            var fragment = GroupCartBottomFragment()
            val args = Bundle().apply {
                putParcelable(GROUP_CART_BOTTOM,groupData)
            }
            fragment.arguments = args
            return fragment
        }

        private const val GROUP_CART_BOTTOM = "argMyData"
    }

}