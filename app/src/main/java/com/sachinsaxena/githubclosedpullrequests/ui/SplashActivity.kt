package com.sachinsaxena.githubclosedpullrequests.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.sachinsaxena.githubclosedpullrequests.databinding.ActivitySplashBinding

/**
Created by Sachin Saxena on 16/06/22.
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goToMainActivity()
    }

    private fun goToMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            MainActivity.getStartIntent(this).apply {
                startActivity(this)
            }
            finish()
        },
            SPLASH_WAITING_TIME
        )
    }

    companion object {
        private const val SPLASH_WAITING_TIME = 2000L // 2 seconds.
    }
}