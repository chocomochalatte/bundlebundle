package com.example.bundlebundle.product.detail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
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
import com.example.bundlebundle.retrofit.dataclass.GroupIdVO
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BottomSheetCartFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvQuantity: TextView
    private var quantity = 1
    private lateinit var intent: Intent

    private val apiService = ApiClient.apiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intent = requireActivity().intent
        tvQuantity = view.findViewById(R.id.tvQuantity)

        binding.bottomSheetPersonalCartButton.setOnClickListener {
            val posListener = DialogInterface.OnClickListener { dialog, _ -> addToPersonalCart() }
            showAlert("개인 장바구니", "개인 장바구니에 추가하시겠습니까?", posListener)
        }

        binding.bottomSheetGroupCartButton.setOnClickListener {
            doActionWithGroupCart()
        }
    }

    private fun doActionWithGroupCart() {
        apiService.checkIfGroupIsPresent("fcdkjchbskjvb").enqueue(object : Callback<GroupIdVO> {
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
        apiService.addToGroupCart().enqueue(object : Callback<GroupMemberCartVO> {
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
        apiService.addToPersonalCart().enqueue(object : Callback<GroupMemberCartVO> {
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
        val newIntent = Intent(context, GroupActivity(GroupCreateFragment.newInstance())::class.java)
        newIntent.putExtra("tab", "group")
        newIntent.putExtra("productId", intent.getIntExtra("productId", -1))
        newIntent.putExtra("productCnt", quantity)
        requireActivity().startActivity(newIntent)
    }
}

