package com.example.bundlebundle.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundlebundle.R

/* 그룹 생성 프래그먼트 */
class GroupCreateFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_create, container, false)
    }

    companion object {
        // 이 팩토리 메서드를 사용하여 제공된 매개변수로 새로운 인스턴스를 생성
        @JvmStatic
        fun newInstance() =
            GroupCreateFragment().apply {
            }
    }
}