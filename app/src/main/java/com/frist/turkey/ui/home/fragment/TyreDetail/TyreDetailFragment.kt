package com.frist.turkey.ui.home.fragment.TyreDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.frist.turkey.R
import com.frist.turkey.base.BaseFragment
import com.frist.turkey.ui.home.fragment.driver.DriverFragment
import kotlinx.android.synthetic.main.fragment_tyre_detail.*


class TyreDetailFragment : BaseFragment(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tyre_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initControl()
    }

    override fun initViews() {

    }

    override fun initControl() {
        btn_saveTyreDetail.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_saveTyreDetail -> {
                if (validateTyreDetail()) {
                    Toast.makeText(requireContext(), "Tyre Detail Validation Done", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateTyreDetail(): Boolean {
        if (etTyre_TrucK_No.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Truck Number", Toast.LENGTH_SHORT).show()
            return false
        } else if (etTyreDetailNoOfTyres.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Number of Tyre", Toast.LENGTH_SHORT).show()
            return false
        } else if (etTyreBrand.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Tyre Brand", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    companion object {
        private const val EXTRA_TEXT = "text"
        fun createFor(text: String?): TyreDetailFragment {
            val fragment = TyreDetailFragment()
            val args = Bundle()
            args.putString(EXTRA_TEXT, text)
            fragment.setArguments(args)
            return fragment
        }
    }

}