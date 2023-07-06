package com.example.bundlebundle.cart

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

