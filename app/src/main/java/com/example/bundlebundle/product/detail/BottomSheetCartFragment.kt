package com.example.bundlebundle.product.detail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.bundlebundle.CartActivity
import com.example.bundlebundle.group.GroupActivity
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentBottomSheetCartBinding
import com.example.bundlebundle.group.GroupCreateFragment
import com.example.bundlebundle.group.GroupMemberCartVO
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.ApiClient.apiService
import com.example.bundlebundle.retrofit.ApiClient.groupApiService
import com.example.bundlebundle.retrofit.dataclass.GroupIdVO
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale


class BottomSheetCartFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvQuantity: TextView
    private var quantity = 1

    private var newProductCnt=0
    private var totalPrice=0

    private lateinit var intent: Intent

    private val cartApiService = ApiClient.cartApiService
    private val groupApiService = ApiClient.groupApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetCartBinding.inflate(inflater, container, false)



        return binding.root
    }

    private fun minusProductCnt() {
        val productCnt = binding.tvQuantity.text.toString().replace(",", "").toInt()
        val price = binding.productPrice.text.toString().replace(",", "").toInt()
        if (productCnt > 1) {
            newProductCnt = productCnt - 1
            binding.tvQuantity.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(newProductCnt)
            totalPrice = newProductCnt * price
            binding.productTotalprice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice)
        }
    }

    private fun plusProductCnt() {
        val productCnt = binding.tvQuantity.text.toString().replace(",", "").toInt()
        val price = binding.productPrice.text.toString().replace(",", "").toInt()
        newProductCnt = productCnt + 1
        binding.tvQuantity.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(newProductCnt)
        totalPrice = newProductCnt * price
        binding.productTotalprice.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intent = requireActivity().intent
        tvQuantity = view.findViewById(R.id.tvQuantity)

        Log.d("aaa","${binding.tvQuantity}")

        // 마이너스 버튼 누른 경우
        binding.btnMinus.setOnClickListener{
            minusProductCnt()
        }

        // 플러스 버튼 누른 경우
        binding.btnPlus.setOnClickListener {
            plusProductCnt()
        }

        binding.bottomSheetPersonalCartButton.setOnClickListener {
            val posListener = DialogInterface.OnClickListener { dialog, _ -> addToPersonalCart() }
            showAlert("개인 장바구니", "개인 장바구니에 추가하시겠습니까?", posListener)
        }

        binding.bottomSheetGroupCartButton.setOnClickListener {
            doActionWithGroupCart()
        }
    }

    private fun doActionWithGroupCart() {
        groupApiService.checkIfGroupIsPresent("fcdkjchbskjvb").enqueue(object : Callback<GroupIdVO> {
            override fun onResponse(call: Call<GroupIdVO>, response: Response<GroupIdVO>) {
                val groupId: Int? = response.body()?.groupId
                when (groupId) {
                    null -> {
                        val posListener = DialogInterface.OnClickListener { dialog, _ -> moveToGroupCreatePage() }
                        showAlert("그룹 장바구니", "그룹 장바구니가 없습니다. 생성하시겠습니까?", posListener)
                    }
                    else -> {
                        val posListener = DialogInterface.OnClickListener { dialog, _ -> addToGroupCart() }
                        showAlert("그룹 장바구니", "그룹 장바구니에 추가하시겠습니까?", posListener)
                    }
                }
            }

            override fun onFailure(call: Call<GroupIdVO>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun addToGroupCart() {
        cartApiService.addToGroupCart().enqueue(object : Callback<GroupMemberCartVO> {
            override fun onResponse(
                call: Call<GroupMemberCartVO>,
                response: Response<GroupMemberCartVO>
            ) {
                val posListener = DialogInterface.OnClickListener { dialog, _ -> moveToCart("group") }
                showAlert("그룹 장바구니에 추가 완료", "그룹 장바구니로 이동하시겠습니까?", posListener)
            }

            override fun onFailure(call: Call<GroupMemberCartVO>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun addToPersonalCart() {
        cartApiService.addToPersonalCart().enqueue(object : Callback<GroupMemberCartVO> {
            override fun onResponse(
                call: Call<GroupMemberCartVO>,
                response: Response<GroupMemberCartVO>
            ) {
                val posListener = DialogInterface.OnClickListener { dialog, _ -> moveToCart("personal") }
                showAlert("개인 장바구니에 추가 완료", "장바구니로 이동하시겠습니까?", posListener)
            }

            override fun onFailure(call: Call<GroupMemberCartVO>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun showAlert(title: String, message: String, positiveListener: DialogInterface.OnClickListener) {
        val negativeListener = DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }

        AlertDialog.Builder(requireActivity(), R.style.MyAlertDialogTheme).run {
            setTitle(title)
            setMessage(message)
            setPositiveButton("확인", positiveListener)
            setNegativeButton("취소", negativeListener)
            create()
        }.show()
    }

    private fun moveToCart(cartType: String) {
        val newIntent = Intent(context, CartActivity::class.java)
        newIntent.putExtra("tab", cartType)
        newIntent.putExtra("productId", intent.getIntExtra("productId", -1))
        newIntent.putExtra("productCnt", quantity)
        requireActivity().startActivity(newIntent)
    }

    private fun moveToGroupCreatePage() {
        val newIntent = Intent(context, GroupActivity()::class.java)
        newIntent.putExtra("tab", "group")
        newIntent.putExtra("pageType", "create")
        newIntent.putExtra("productId", intent.getIntExtra("productId", -1))
        newIntent.putExtra("productCnt", quantity)
        requireActivity().startActivity(newIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

