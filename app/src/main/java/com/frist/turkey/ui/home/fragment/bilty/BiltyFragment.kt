package com.frist.turkey.ui.home.fragment.bilty


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.frist.turkey.R
import com.frist.turkey.base.BaseFragment
import com.frist.turkey.utils.shareDoucments
import kotlinx.android.synthetic.main.dialog_share_builty.*
import kotlinx.android.synthetic.main.dialog_share_builty.view.*
import kotlinx.android.synthetic.main.fragment_bilty.*


class BiltyFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bilty, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initControl()
    }

    override fun initViews() {

    }

    override fun initControl() {
        tvShareBilty.setOnClickListener {
            openShareDialogue()
        }
    }


    private fun openShareDialogue() {
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_share_builty, null)
        var dialog = AlertDialog.Builder(context, 0).create()
        dialog.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        dialog.apply {
            setView(view)

            view.tvNo.setOnClickListener {
                dialog.dismiss()
            }
            view.tvYes.setOnClickListener {
                shareBuilty()
                dialog.dismiss()
            }
            setCancelable(false)
            setCanceledOnTouchOutside(false)

        }.show()

    }

    private fun shareBuilty() {
        shareDoucments()
    }


    companion object {
        private const val EXTRA_TEXT = "text"
        fun createFor(text: String?): BiltyFragment {
            val fragment = BiltyFragment()
            val args = Bundle()
            args.putString(EXTRA_TEXT, text)
            fragment.setArguments(args)
            return fragment
        }
    }
}