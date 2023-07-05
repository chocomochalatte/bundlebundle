package com.example.bundlebundle

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundlebundle.databinding.FragmentEmptyGroupCartBinding
import com.example.bundlebundle.group.GroupActivity
import com.example.bundlebundle.group.GroupCreateFragment


class EmptyGroupCartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEmptyGroupCartBinding.inflate(inflater, container,false)

        binding.createGroupCartBtn.setOnClickListener {
            val intent = Intent(context, GroupActivity()::class.java)
            intent.putExtra("pageType", "create")
            startActivity(intent)
        }

        return binding.root
    }

}