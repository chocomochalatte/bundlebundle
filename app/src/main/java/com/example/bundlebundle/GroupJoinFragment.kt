package com.example.bundlebundle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class GroupJoinFragment : Fragment() {

    private var groupOwnerName: String? = null

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
        val view = inflater.inflate(R.layout.fragment_group_join, container, false)

        val textView: TextView = view.findViewById(R.id.group_join_text)
        val editText: EditText = view.findViewById(R.id.group_join_edit_text)
        val button: Button = view.findViewById(R.id.group_join_btn)

        // 동적으로 데이터 설정
        textView.text = "${groupOwnerName}님과 함께 먹을\n메뉴를 골라보세요"

        return view
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
