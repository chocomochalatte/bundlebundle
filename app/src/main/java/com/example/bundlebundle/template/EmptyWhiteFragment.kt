package com.example.bundlebundle.template

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentEmptyWhiteBinding


class EmptyWhiteFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEmptyWhiteBinding.inflate(layoutInflater)
        return binding.root
    }

}