package com.frist.turkey.base

import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.frist.turkey.utils.SharedPreferenceUtility

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    val sharedPreferences by lazy {
        SharedPreferenceUtility.getInstance(this.applicationContext)
    }

    // Initialization only view and set text views
    abstract fun initViews()

    // Set Listeners or events and methods
    abstract fun initControl()



}