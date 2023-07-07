package com.example.bundlebundle.cart

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentGroupCartBottomBinding
import com.example.bundlebundle.order.OrderActivity
import com.example.bundlebundle.order.OrderStepActivity
import com.example.bundlebundle.retrofit.ApiClient.orderApiService
import com.example.bundlebundle.retrofit.dataclass.cart.GroupCartListVO
import com.example.bundlebundle.retrofit.dataclass.order.GroupOrderVO
import com.example.bundlebundle.util.OrderCompleteDialog
import com.example.bundlebundle.util.ServerResponseErrorDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale


class GroupCartBottomFragment : Fragment() {
    private var _binding: FragmentGroupCartBottomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupCartBottomBinding.inflate(layoutInflater)
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

            if(groupresultprice<=0){
                groupresultprice=0
                val resultpriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(groupresultprice)
                binding.groupcartitemResultprice.text = resultpriceFormatted
            }else{
                val resultpriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(groupresultprice)
                binding.groupcartitemResultprice.text = resultpriceFormatted
            }



            binding.groupCartTotalCnt.text = grouporderCnt.toString()

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.groupCartOrderBtn.setOnClickListener {
            val intent = Intent(requireContext(), OrderStepActivity::class.java)
            startActivity(intent)
        }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}