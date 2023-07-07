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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentOrderListBinding
import com.example.bundlebundle.product.slider.ProductSliderFragment
import com.example.bundlebundle.product.slider.ViewPagerFragment
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.order.ProductOrderVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.properties.Delegates


class OrderListFragment : Fragment() {
    private val orderApiService = ApiClient.orderApiService

    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    private lateinit var intent: Intent
    private var orderId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentFragmentManager.beginTransaction()
            .add(R.id.adv_slider_area, ProductSliderFragment())
            .commit()
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
        orderId = requireActivity().intent.getIntExtra("orderId", -1)

        lifecycleScope.launch {
            val result: List<ProductOrderVO> = getResultFromApi(orderId)!!
        }

        binding.orderedAddress.text = intent.getStringExtra("address")
    }

    private suspend fun getResultFromApi(orderId: Int): List<ProductOrderVO>? {
        return withContext(Dispatchers.IO) {
            suspendCoroutine<List<ProductOrderVO>?> { continuation ->
                val call = orderApiService.showOrderProducts(orderId)
                call.enqueue(object : Callback<List<ProductOrderVO>> {
                    override fun onResponse(
                        call: Call<List<ProductOrderVO>>,
                        response: Response<List<ProductOrderVO>>
                    ) {
                        when (response.isSuccessful) {
                            true -> {
                                binding.orderItems.layoutManager = LinearLayoutManager(requireContext())
                                binding.orderItems.adapter = OrderViewAdapter(response.body()!!)
                            }
                            false -> showAlert("ERROR", "서버에서 오류가 발생했습니다.", { dialog, _ -> })
                        }
                    }

                    override fun onFailure(call: Call<List<ProductOrderVO>>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
            }
        }
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