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
import com.example.bundlebundle.R
import com.example.bundlebundle.cart.CartActivity
import com.example.bundlebundle.databinding.FragmentGroupJoinBinding
import com.example.bundlebundle.product.list.ProductPageActivity
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.group.GroupMemberVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class GroupJoinFragment : Fragment() {
    val TAG = "GroupJoinFragment"

    private var _binding: FragmentGroupJoinBinding? = null
    private val binding get() = _binding!!

    private var groupOwnerName: String? = null
    private var groupId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupOwnerName = it.getString(GROUP_OWNER_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        _binding = FragmentGroupJoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.groupJoinText.text = "${groupOwnerName}님과 함께 먹을\n메뉴를 골라보세요"

        binding.groupJoinBtn.setOnClickListener {
            joinGroup()
        }

        groupId = requireActivity().intent.getIntExtra("groupId", -1)
    }

    private fun joinGroup() {
        val nickname: String = binding.groupJoinEditText.text.toString()
        val requestVO = GroupMemberVO(groupId, -1, nickname)
        val call: Call<GroupMemberVO> = ApiClient.groupApiService.joinGroup(requestVO)
        call.enqueue(object : Callback<GroupMemberVO> {
            override fun onResponse(call: Call<GroupMemberVO>, response: Response<GroupMemberVO>) {
                if (response.isSuccessful) {
                    val posListener = DialogInterface.OnClickListener { dialog, _ -> moveToCart("group", response.body()!!.groupId) }
                    Log.d(TAG, "posListener 실행완료")
                    showAlert("그룹 장바구니 참여 완료", "그룹 장바구니로 이동하시겠습니까?", posListener)
                } else {
                    showAlert("ERROR : ${response.body()}", "서버에서 그룹 장바구니 참여에 실패하였습니다. 메인 화면으로 돌아갑니다.", DialogInterface.OnClickListener { dialog, _ -> moveToMain() })
                }
            }

            override fun onFailure(call: Call<GroupMemberVO>, t: Throwable) {
                t.printStackTrace()
                Log.d(TAG, t.toString())
                showAlert("ERROR : ${t.message}", "응답이 실패하였습니다. 메인 화면으로 돌아갑니다.", DialogInterface.OnClickListener { dialog, _ -> moveToMain() })
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
        private const val GROUP_OWNER_NAME = "groupOwnerName"

        @JvmStatic
        fun newInstance(groupOwnerName: String) =
            GroupJoinFragment().apply {
                arguments = Bundle().apply {
                    putString(GROUP_OWNER_NAME, groupOwnerName)
                }
            }
    }
}
