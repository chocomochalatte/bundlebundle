package com.example.bundlebundle.global

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import com.example.bundlebundle.MyFirebaseMessagingService
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.ActivityToastBinding
import com.google.firebase.messaging.FirebaseMessaging

class ToastActivity : AppCompatActivity() {
    lateinit var binding: ActivityToastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToastBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /** FCM설정, Token값 가져오기 */
        MyFirebaseMessagingService().getFirebaseToken()
        Log.d("hong","${MyFirebaseMessagingService().getFirebaseToken()}")

    }

}