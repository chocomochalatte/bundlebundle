package com.example.bundlebundle.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.ActivityOrderStepBinding
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.order.GroupOrderVO
import com.example.bundlebundle.util.OrderCompleteDialog
import com.example.bundlebundle.util.ServerResponseErrorDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderStepActivity : AppCompatActivity() {

    private val TAG = "OrderStepActivity"

    private lateinit var binding: ActivityOrderStepBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderStepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var searchBtn = findViewById<androidx.appcompat.widget.AppCompatImageButton>(R.id.searchAddressBtn)
        searchBtn.setOnClickListener{
            val intent = Intent(this@OrderStepActivity,OrderAddressActivity::class.java)
            startActivity(intent)
        }

        val intentData = intent.getStringExtra("addressText")
        var location = findViewById<TextView>(R.id.locationText)
        if (intentData != null) {
            location.setText(intentData)
        } else {
            location.setText("주소를 입력해주세요")
        }

        binding.btnMakeOrderWithAddress.setOnClickListener {
            makeWholeGroupCartOrder()
        }
    }

    private fun makeWholeGroupCartOrder() {

        ApiClient.orderApiService.makeWholeGroupCartOrder().enqueue(object: Callback<GroupOrderVO> {
            override fun onResponse(
                call: Call<GroupOrderVO>,
                response: Response<GroupOrderVO>
            ) {
                when (response.isSuccessful) {
                    true -> {
                        val orderId: Int = response.body()!!.id
                        val dialog = OrderCompleteDialog(this@OrderStepActivity)
                        dialog.listener = object : OrderCompleteDialog.LessonDeleteDialogClickedListener {
                            override fun onDeleteClicked() {
                                moveToOrderResult(orderId)
                            }
                        }
                        dialog.start()
                    }

                    else -> {
                        val dialog = ServerResponseErrorDialog(this@OrderStepActivity)
                        Log.d(TAG, "주문하기 서버 오류")
                        dialog.listener = object : ServerResponseErrorDialog.LessonDeleteDialogClickedListener {
                            override fun onDeleteClicked() {
                                //Access
                            }
                        }
                        dialog.start()
                    }
                }
            }

            override fun onFailure(call: Call<GroupOrderVO>, t: Throwable) {
                Log.d(TAG, "주문하기 요청 오류")
                val dialog = ServerResponseErrorDialog(this@OrderStepActivity)
                dialog.listener = object : ServerResponseErrorDialog.LessonDeleteDialogClickedListener {
                    override fun onDeleteClicked() {
                    }
                }
                dialog.start()
            }
        })
    }

    private fun moveToOrderResult(orderId: Int) {
        val intent = Intent(this, OrderActivity::class.java)
        val address = binding.locationText.text
        intent.putExtra("orderId", orderId)
        intent.putExtra("address", address)
        startActivity(intent)
    }
}
