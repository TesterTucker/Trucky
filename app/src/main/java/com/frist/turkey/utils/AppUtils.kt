package com.frist.turkey.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.frist.turkey.R

object AppUtils {

    fun replaceFragment(
        activity: FragmentActivity,
        fragment: Fragment,
        @IdRes holder: Int,
        addToBackStack: Boolean = true,
        showSlideAnimation: Boolean = true,
        vararg transitions: View,
        commitNow: Boolean = false
    ) {
        val ft = activity.supportFragmentManager.beginTransaction()
        if (showSlideAnimation)
            ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_in_right, R.animator.slide_out_left)
        ft.replace(holder, fragment, fragment.javaClass.simpleName)
        transitions.forEach { ft.addSharedElement(it, it.transitionName) }
        if (addToBackStack)
            ft.addToBackStack(fragment.javaClass.simpleName)
        if (commitNow) ft.commitNow() else ft.commit()
    }

    fun addFragment(activity: FragmentActivity, fragment: Fragment, @IdRes holder: Int, hideFragment: Fragment? = null, addToBackStack: Boolean = true) {
        val ft = activity.supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.animator.slide_in_left,
                R.animator.slide_out_right, R.animator.slide_in_right, R.animator.slide_out_left)
        hideFragment?.let { ft.hide(hideFragment) }
        ft.add(holder, fragment, fragment.javaClass.simpleName)
        if (addToBackStack) ft.addToBackStack(fragment.javaClass.simpleName)
        ft.commit()
    }

    fun replaceFragment(
        activity: FragmentActivity,
        fragment: Fragment,
        @IdRes holder: Int,
        addToBackStack: Boolean = true,
        showSlideAnimation: Boolean = true,
        vararg transitions: View,
        commitNow: Boolean = false,
        tag: String
    ) {
        val ft = activity.supportFragmentManager.beginTransaction()
        if (showSlideAnimation)
            ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_in_right, R.animator.slide_out_left)
        ft.replace(holder, fragment, tag)
        transitions.forEach { ft.addSharedElement(it, it.transitionName) }
        if (addToBackStack)
            ft.addToBackStack(tag)
        if (commitNow) ft.commitNow() else ft.commit()
    }
}