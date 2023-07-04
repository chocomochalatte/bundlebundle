package com.example.bundlebundle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundlebundle.databinding.FragmentCartBottomBinding
import com.example.bundlebundle.retrofit.dataclass.CartVO
import java.text.NumberFormat
import java.util.Locale


class CartBottomFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCartBottomBinding.inflate(layoutInflater)
        arguments?.let {
            val myData = it.getParcelable<CartVO>(CartBottomFragment.MY_CART_BOTTOM)

            val cartProductCount = myData?.cartCnt ?: 0
            var totalprice:Int = 0
            var discountprice:Int = 0
            var shipmentprice:Int = 0
            var resultprice:Int = 0
            var orderCnt:Int = cartProductCount

            for (i in 0 until cartProductCount) {
                val currentItem = myData?.cartProducts?.get(i)
                totalprice += currentItem?.productPrice?.times(currentItem?.productCnt!!) ?: 0
                val discountRate = (currentItem?.discountRate ?: 0) / 100.0 // 비율로 변환
                val discount = (discountRate * (currentItem?.productPrice ?: 0)).toInt()
                discountprice += discount * (currentItem?.productCnt ?: 0)
            }

            if(totalprice-discountprice>=50000){
                shipmentprice = 0
            }else{
                shipmentprice = 3500
            }
            resultprice = totalprice-discountprice-shipmentprice

            val totalpriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalprice)
            binding.mycartitemTotalprice.text = totalpriceFormatted

            val discountpriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(discountprice)
            binding.mycartitemDiscountprice.text = discountpriceFormatted

            val shipmentpriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(shipmentprice)
            binding.mycartItemShipprice.text = shipmentpriceFormatted

            val resultpriceFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(resultprice)
            binding.mycartitemResultprice.text = resultpriceFormatted

            val orderCntFormatted = NumberFormat.getNumberInstance(Locale.getDefault()).format(orderCnt)
            binding.myCartorderCnt.text = orderCntFormatted

        }
        return binding.root
    }

    fun newInstance(myData: CartVO): Fragment {
        var fragment = CartBottomFragment()
        val args = Bundle().apply {
            putParcelable(CartBottomFragment.MY_CART_BOTTOM,myData)
        }
        fragment.arguments = args
        return fragment
    }



    companion object {
        fun newInstance(data: CartVO): Fragment {
            var fragment = CartBottomFragment()
            val args = Bundle().apply {
                putParcelable(CartBottomFragment.MY_CART_BOTTOM,data)
            }
            fragment.arguments = args
            return fragment
        }

        private const val MY_CART_BOTTOM = "argMyData"
    }

}