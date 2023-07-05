package com.example.bundlebundle.order

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
//import androidx.fragment.app.FragmentManager
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentOrderListBinding
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.order.ProductOrderVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderListFragment : Fragment() {
    private val orderApiService = ApiClient.orderApiService

    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intent = requireActivity().intent

        val orderProducts = getResultFromApi()?: emptyList()
        binding.orderItems.adapter = OrderViewAdapter(orderProducts)
    }

    private fun getResultFromApi(): List<ProductOrderVO>? {
        val orderId = requireActivity().intent.getIntExtra("orderId", -1)
        var result: List<ProductOrderVO> ?= null

        orderApiService.showOrderProducts(orderId).enqueue(object : Callback<List<ProductOrderVO>> {
            override fun onResponse(
                call: Call<List<ProductOrderVO>>,
                response: Response<List<ProductOrderVO>>
            ) {
                when (response.isSuccessful) {
                    true -> {
                        result = response.body()
                    }

                    else -> {
                        showAlert("ERROR", "서버에서 오류가 발생했습니다.", { dialog, _ -> })
                    }
                }
            }

            override fun onFailure(call: Call<List<ProductOrderVO>>, t: Throwable) {
                showAlert("ERROR", "서버 응답이 실패했습니다.", { dialog, _ -> })
            }
        })
        return result
    }

    private fun showAlert(
        title: String,
        message: String,
        positiveListener: DialogInterface.OnClickListener
    ) {
        val negativeListener =
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }

        AlertDialog.Builder(requireActivity(), R.style.MyAlertDialogTheme).run {
            setTitle(title)
            setMessage(message)
            setPositiveButton("확인", positiveListener)
            setNegativeButton("취소", negativeListener)
            create()
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}