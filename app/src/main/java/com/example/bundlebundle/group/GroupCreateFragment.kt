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
import com.example.bundlebundle.retrofit.FBSApiClient
import com.example.bundlebundle.retrofit.dataclass.firebase.FcmData
import com.example.bundlebundle.retrofit.dataclass.firebase.FcmMessageVO
import com.example.bundlebundle.retrofit.dataclass.firebase.FcmResponse
import com.example.bundlebundle.retrofit.dataclass.group.GroupNicknameVO
import com.example.bundlebundle.retrofit.dataclass.group.GroupVO
import com.example.bundlebundle.util.GroupCartEndDialog
import com.example.bundlebundle.util.ServerResponseErrorDialog
import com.google.firebase.messaging.FirebaseMessaging
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
        var token: String? = null

        setToken { retrievedToken, exception ->
            if (retrievedToken != null) {
                token = retrievedToken
                Log.d("hong","$token")
                // token이 firebase 토큰이어서 여기에서 백엔드로 넘기면 될 것 같습니다.
                val call: Call<GroupVO> = groupApiService.createGroup(GroupNicknameVO(nickname,token!!))
                call.enqueue(object : Callback<GroupVO> {
                    override fun onResponse(call: Call<GroupVO>, response: Response<GroupVO>) {
                        if (response.isSuccessful) {
                            val dialog = GroupCartEndDialog(requireContext())
                            dialog.listener = object : GroupCartEndDialog.LessonDeleteDialogClickedListener {
                                override fun onDeleteClicked() {
                                    moveToCart("group", response.body()!!.id)
                                }
                            }
                            dialog.start()

                            // 알림 처리하는 로직
                            if (token != null) {
                                // token 값이 정상적으로 설정되었을 때의 처리 로직
                                val fbsapiService = FBSApiClient.fbsapiService
                                val fcmData = FcmData("그룹 장바구니 생성!!", "상품을 담으러 가보세요~~")
                                val message = FcmMessageVO(token!!, "high", fcmData)
                                val call = fbsapiService.alarm(message)

                                call.enqueue(object: Callback<FcmResponse> {
                                    override fun onResponse(call: Call<FcmResponse>, response: Response<FcmResponse>) {
                                        val responseData = response.body()
                                        when(response.isSuccessful) {
                                            true -> Log.d("aaaa","${responseData}")
                                            else -> Log.d("bbbbb",response.code().toString() + response.errorBody().toString() + response.message())
                                        }
                                    }
                                    override fun onFailure(call: Call<FcmResponse>, t: Throwable) {
                                        call.cancel()
                                    }
                                })

                            } else {
                                // token이 null인 경우 예외 처리 로직
                                Log.e("aaaa", "Failed to retrieve token")
                            }
                        } else {
                            showAlert("ERROR : ${response.body()}", "그룹 장바구니 생성이 실패하였습니다. 메인 화면으로 돌아갑니다.", DialogInterface.OnClickListener { dialog, _ -> moveToMain() })
                        }
                    }

                    override fun onFailure(call: Call<GroupVO>, t: Throwable) {
                        t.printStackTrace()
                        showAlert("ERROR : ${t.message}", "그룹 장바구니 생성이 실패하였습니다. 메인 화면으로 돌아갑니다.", DialogInterface.OnClickListener { dialog, _ -> moveToMain() })
                    }
                })
            } else {
                // 예외 처리 로직
                Log.e("aaaa", "Failed to retrieve token: ${exception?.message}")
            }
        }
    }

    private fun setToken(callback: (String?, Exception?) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener{task->
                if (task.isSuccessful) {
                    Log.d("aaaa","${task.result}")
                    callback(task.result, null)
                } else {
                    val exception = task.exception?: Exception("Failed to retrieve token")
                    callback(null, exception)
                }
            }
    }

    private fun moveToCart(startingTab: String, groupId: Int) {
        Log.d("ming", groupId.toString())
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