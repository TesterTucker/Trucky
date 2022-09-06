package com.frist.turkey.ui.home.fragment.ClientDetailFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.frist.turkey.R
import com.frist.turkey.base.BaseFragment
import com.frist.turkey.ui.home.fragment.TyreDetail.TyreDetailFragment
import kotlinx.android.synthetic.main.fragment_client_detail.*


class ClientDetailFragment : BaseFragment(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initControl()

    }

    override fun initViews() {

    }

    override fun initControl() {
        btn_saveClientDetail.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_saveClientDetail->{
                if (validateClientDetail()){
                    Toast.makeText(requireContext(), "Client Validation Done", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val EXTRA_TEXT = "text"
        fun createFor(text: String?): ClientDetailFragment {
            val fragment = ClientDetailFragment()
            val args = Bundle()
            args.putString(EXTRA_TEXT, text)
            fragment.setArguments(args)
            return fragment
        }
    }

    fun validateClientDetail():Boolean{
        if (etClientName.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Entter Name", Toast.LENGTH_SHORT).show()
        }else  if (etClientDetail_PhoneNumber.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Client Mobile Number", Toast.LENGTH_SHORT).show()
        }else  if (etClientDetailTypeOfClient.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Type of Client", Toast.LENGTH_SHORT).show()
        }else  if (etClientDetailCompanyName.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Company Name", Toast.LENGTH_SHORT).show()
        }else  if (etClientDetailDriverAddress.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Address", Toast.LENGTH_SHORT).show()
        }
        return true
    }


}