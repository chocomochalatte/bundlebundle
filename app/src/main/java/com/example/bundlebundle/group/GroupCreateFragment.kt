package com.example.bundlebundle.group

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.bundlebundle.cart.CartActivity
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentGroupCreateBinding
import com.example.bundlebundle.product.list.ProductPageActivity
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.ApiClient.groupApiService
import com.example.bundlebundle.retrofit.dataclass.group.GroupNicknameVO
import com.example.bundlebundle.retrofit.dataclass.group.GroupVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* 그룹 생성 프래그먼트 */
class GroupCreateFragment : Fragment() {
    private var _binding: FragmentGroupCreateBinding? = null
    private val binding get() = _binding!!

    private val cartApiService = ApiClient.groupApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnGroupCreate = binding.btnGroupCreate
        btnGroupCreate.setOnClickListener {
            createGroupCart()
        }
    }

    private fun createGroupCart() {
        val nickname: String = binding.editTextGroupNickname.text.toString()
        val call: Call<GroupVO> = groupApiService.createGroup(GroupNicknameVO(nickname))
        call.enqueue(object : Callback<GroupVO> {
            override fun onResponse(call: Call<GroupVO>, response: Response<GroupVO>) {
                if (response.isSuccessful) {
                    Log.d("ming", GroupNicknameVO(nickname).toString())
                    val posListener = DialogInterface.OnClickListener { dialog, _ -> moveToCart("group", response.body()!!.id) }
                    showAlert("그룹 장바구니 생성 완료", "그룹 장바구니로 이동하시겠습니까?", posListener)
                } else {
                    Log.d("Group Create Fragment", response.body().toString())
                    showAlert("ERROR : ${response.body()}", "그룹 장바구니 생성이 실패하였습니다. 메인 화면으로 돌아갑니다.", DialogInterface.OnClickListener { dialog, _ -> moveToMain() })
                }
            }

            override fun onFailure(call: Call<GroupVO>, t: Throwable) {
                t.printStackTrace()
                Log.d("Group Create Fragment", t.toString())
                showAlert("ERROR : ${t.message}", "그룹 장바구니 생성이 실패하였습니다. 메인 화면으로 돌아갑니다.", DialogInterface.OnClickListener { dialog, _ -> moveToMain() })
            }
        })
    }

    private fun moveToCart(startingTab: String, groupId: Int) {
        val intent = Intent(requireActivity(), CartActivity::class.java)
        intent.putExtra("tab", startingTab)
        intent.putExtra("groupId", groupId)
        startActivity(intent)
    }

    private fun moveToMain() {
        val intent = Intent(requireActivity(), ProductPageActivity::class.java)
        startActivity(intent)
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

    companion object {
        @JvmStatic
        fun newInstance() =
            GroupCreateFragment().apply {
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}