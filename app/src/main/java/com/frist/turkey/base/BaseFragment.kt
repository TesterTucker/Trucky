package com.frist.turkey.base

import androidx.fragment.app.Fragment
import com.frist.turkey.utils.SharedPreferenceUtility

abstract class BaseFragment: Fragment() {


    val sharedPreferences by lazy {
        SharedPreferenceUtility.getInstance(requireActivity().applicationContext)
    }

    // Initialization only view and set text views
    abstract fun initViews()

    // Set Listeners or events and methods
    abstract fun initControl()

    /*Could handle back press from Fragments.
    @return true if back press was handled*/
    open fun onBackPressed(): Boolean {
        return false
    }



}