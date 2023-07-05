package com.example.bundlebundle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bundlebundle.databinding.ActivityToastBinding

class ToastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityToastBinding.inflate(layoutInflater)
        binding.toastbutton.setOnClickListener {
            val intent = Intent(this, ToastOtherActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }
}