package com.template.basestructure.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.template.basestructure.MainActivity
import com.template.basestructure.base.BaseActivity
import com.template.basestructure.databinding.ActivitySplashBinding


class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}