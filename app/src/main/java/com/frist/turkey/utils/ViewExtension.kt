package com.frist.turkey.utils


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.frist.turkey.R




fun Fragment.statusBarTransparent() {
    if (Build.VERSION.SDK_INT in 19..20) {
        setWindowFlag(
            requireActivity(),
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            true
        )
    }
    if (Build.VERSION.SDK_INT >= 19) {
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    if (Build.VERSION.SDK_INT >= 21) {
        setWindowFlag(
            requireActivity(),
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            false
        )
        requireActivity().window.statusBarColor = Color.TRANSPARENT
    }
}

interface SpinnerCallback {
    fun onSelected(value: String)
}

fun Activity.spinnerMonth(
    anchorView: TextView,
    list: ArrayList<String>,
    callback: SpinnerCallback
) {
    val popup = PopupMenu(this, anchorView)
    for (i in 0 until list.size) {
        popup.menu.add(list[i])
    }
    popup.setOnMenuItemClickListener { item ->
        anchorView.setText(item.title)
        callback.onSelected(item.title.toString())
        true
    }
    popup.show()
}

fun Fragment.spinnerMonth(
    anchorView: TextView,
    list: ArrayList<String>,
    callback: SpinnerCallback
) {
    val popup = PopupMenu(requireContext(), anchorView)
    for (i in 0 until list.size) {
        popup.menu.add(list[i])
    }
    popup.setOnMenuItemClickListener { item ->
        anchorView.setText(item.title)
        callback.onSelected(item.title.toString())
        true
    }
    popup.show()
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.moveActivity(activity: Activity) {
    var intent = Intent(this, activity::class.java)
    startActivity(intent)
}

fun Fragment.moveFragment(activity: Activity) {
    var intent = Intent(requireActivity(), activity::class.java)
    startActivity(intent)
}

fun AppCompatActivity.statusBarTransparent() {
    if (Build.VERSION.SDK_INT in 19..20) {
        setWindowFlag(
            this,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            true
        )
    }
    if (Build.VERSION.SDK_INT >= 19) {
        this.window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    if (Build.VERSION.SDK_INT >= 21) {
        setWindowFlag(
            this,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            false
        )
        this.window.statusBarColor = Color.TRANSPARENT
    }
}

fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
    val win = activity.window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}








