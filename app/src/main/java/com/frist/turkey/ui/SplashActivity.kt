package com.frist.turkey.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.frist.turkey.R
import com.frist.turkey.base.BaseActivity
import com.frist.turkey.ui.home.HomeActivity
import com.frist.turkey.ui.login.LoginActivity
import com.frist.turkey.utils.statusBarTransparent

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        statusBarTransparent()
        initViews()
        initControl()
        Handler().postDelayed({

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1500)

    }

    override fun initViews() {
/*
        if (sharedPreferences.isLoggedIn){
            startActivity(Intent(this, HomeActivity::class.java))
        }else{
            startActivity(Intent(this, SplashActivity::class.java))
        }
*/

    }

    override fun initControl() {

    }
}