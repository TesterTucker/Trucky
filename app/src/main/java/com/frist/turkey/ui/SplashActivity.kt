package com.frist.turkey.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.frist.turkey.R
import com.frist.turkey.ui.login.LoginActivity
import com.frist.turkey.utils.statusBarTransparent

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        statusBarTransparent()
        Handler().postDelayed({

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1500)

    }
}