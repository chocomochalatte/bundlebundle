package com.example.bundlebundle.cart

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import com.example.bundlebundle.Firebase.DynamicLinkUtils
import com.example.bundlebundle.databinding.FragmentGroupCartTopBarBinding
import com.example.bundlebundle.retrofit.dataclass.cart.GroupCartListVO
import com.kakao.sdk.common.util.KakaoCustomTabsClient

class GroupCartTopBarFragment : Fragment() {
    val TAG = "GroupCartTopBarFragment"

    private lateinit var binding: FragmentGroupCartTopBarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupCartTopBarBinding.inflate(layoutInflater)
        arguments?.let {
            val groupData = it.getParcelable<GroupCartListVO>(GROUP_CART_TOTAL_CNT)
            val groupCartCnt = groupData?.totalCnt.toString()
            binding.groupcartitemCnt.text = groupCartCnt

//            binding.btnInviteGroupCart.setOnClickListener {
//                val groupId = requireActivity().intent.getIntExtra("groupId", -1)
//                val scheme = "group-cart"
//                val key = "groupId"
//                DynamicLinkUtils.onDynamicLinkClick(requireActivity(), scheme, key, groupId) { dynamicLink ->
//                    if (dynamicLink != null) {
//                        // 생성된 동적 링크를 사용하여 원하는 작업 수행
//                        val dynamicLinkUrl = dynamicLink.toString()
//                        Log.i(TAG, "Generated Dynamic Link: $dynamicLinkUrl")
//                        // ...
//                    } else {
//                        Log.e(TAG, "kfn")
//                    }
//                }
//            }

            binding.btnInviteGroupCart.setOnClickListener {
                val link = "https://bundlebundle.page.link/group-cart"

                val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("label", link)
                clipboardManager.setPrimaryClip(clipData)

//                // 사용자 정의 메시지 ID
//                //  * 만들기 가이드: https://developers.kakao.com/docs/latest/ko/message/message-template
//                val templateId = templateIds["customMemo"] as Long
//
//                // 콜백 파라미터 설정
//                val serverCallbackArgs = mapOf(templateId to "templateId")
//
//                // 카카오톡 설치여부 확인
//                if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
//                    // 카카오톡으로 카카오톡 공유 가능
//                    ShareClient.instance.shareCustom(context, templateId, serverCallbackArgs) { sharingResult, error ->
//                        if (error != null) {
//                            Log.e(TAG, "카카오톡 공유 실패", error)
//                        }
//                        else if (sharingResult != null) {
//                            Log.d(TAG, "카카오톡 공유 성공 ${sharingResult.intent}")
//                            startActivity(sharingResult.intent)
//
//                            // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
//                            Log.w(TAG, "Warning Msg: ${sharingResult.warningMsg}")
//                            Log.w(TAG, "Argument Msg: ${sharingResult.argumentMsg}")
//                        }
//                    }
//                } else {
//                    // 카카오톡 미설치: 웹 공유 사용 권장
//                    // 웹 공유 예시 코드
//                    val sharerUrl = WebSharerClient.instance.makeCustomUrl(templateId, serverCallbackArgs)
//
//                    // CustomTabs으로 웹 브라우저 열기
//
//                    // 1. CustomTabsServiceConnection 지원 브라우저 열기
//                    // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
//                    try {
//                        KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
//                    } catch(e: UnsupportedOperationException) {
//                        // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
//                    }
//
//                    // 2. CustomTabsServiceConnection 미지원 브라우저 열기
//                    // ex) 다음, 네이버 등
//                    try {
//                        KakaoCustomTabsClient.open(context, sharerUrl)
//                    } catch (e: ActivityNotFoundException) {
//                        // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
//                    }
//                }
            }

        }
        return binding.root
    }

    fun newInstance(groupData: GroupCartListVO): GroupCartTopBarFragment {
        val fragment = GroupCartTopBarFragment()
        val args = Bundle().apply {
            putParcelable(GROUP_CART_TOTAL_CNT, groupData)
        }
        fragment.arguments = args
        return fragment
    }

    companion object {
        private const val GROUP_CART_TOTAL_CNT = "groupCartItem"

        fun newInstance(groupData: GroupCartListVO): GroupCartTopBarFragment {
            val fragment = GroupCartTopBarFragment()
            val args = Bundle().apply {
                putParcelable(GROUP_CART_TOTAL_CNT, groupData)
            }
            fragment.arguments = args
            return fragment
        }
    }



}

