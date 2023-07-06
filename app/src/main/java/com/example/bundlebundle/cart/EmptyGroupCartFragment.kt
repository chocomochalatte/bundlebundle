package com.example.bundlebundle.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundlebundle.databinding.FragmentEmptyGroupCartBinding
import com.example.bundlebundle.group.GroupActivity
import com.google.firebase.messaging.FirebaseMessaging


class EmptyGroupCartFragment : Fragment() {
    var token: String = ""
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