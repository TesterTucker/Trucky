package com.frist.turkey.ui.home.fragment.truckDetail


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.frist.turkey.R
import com.frist.turkey.base.BaseFragment
import com.frist.turkey.ui.home.fragment.HomeFragment
import com.frist.turkey.utils.DatePickerHelper
import com.google.firebase.database.core.Context
import kotlinx.android.synthetic.main.fragment_truck_detail.*
import java.util.*

class TruckDetailFragment : BaseFragment(), View.OnClickListener {
    lateinit var datePicker: DatePickerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_truck_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initControl()
    }

    override fun initViews() {
        datePicker = DatePickerHelper(requireContext(), false)
    }

    override fun initControl() {
        btn_saveTruckDetail.setOnClickListener(this)
        cardTruckPurchaseDate.setOnClickListener(this)
        etInsuranceDate.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
       when(p0?.id){
           R.id.btn_saveTruckDetail->{
               if (validateTruckDetail()){
                   Toast.makeText(context, "Validation Done", Toast.LENGTH_SHORT).show()
               }
           }
           R.id.cardTruckPurchaseDate->{
               showDatePickerDialog()
           }
           R.id.etInsuranceDate->{
               showDatePickerDialog()

           }
       }
    }
    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                etTruckPurchaseDate.text = "${dayStr}-${monthStr}-${year}"

            }
        })
    }
    companion object {
        private const val EXTRA_TEXT = "text"
        fun createFor(text: String?): TruckDetailFragment {
            val fragment = TruckDetailFragment()
            val args = Bundle()
            args.putString(EXTRA_TEXT, text)
            fragment.setArguments(args)
            return fragment
        }
    }

    fun validateTruckDetail():Boolean{
        if (etDealerName.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Dealear Name", Toast.LENGTH_SHORT).show()
            return false
        }else if (ettvTruckBrand.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Truck Brand", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTruckRate.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Truck Rate", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPurchaseDate.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Purchase Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (ettvModel.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Model", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTruckNumber.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Truck Number", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTruckPurchaseDate.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Truck Purchased Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (etChaiseNumber.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Truck Chaise Number ", Toast.LENGTH_SHORT).show()
            return false
        }else if (ettvNoOfTyres.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Truck Number Of Tyres ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etCompanyName.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Insurenece Company Name ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etInsuranceDate.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Insurenece Date ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etRenewalDate.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Insurenece Renewal Date ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etInsuranceNo.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Insurenece No  ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etRenewalAfterDay.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Insurenece Renewal Day  ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etRenewalAfterMonth.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Insurenece Renewal Month  ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitnessCompanyName.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Fitness Company Name  ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitnessDate.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Fitness Date   ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitness_Renewal_Date.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Fitness Renewal Date   ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitnessNo.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Fitness Number", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitness_RenewalAfterDay.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Fitness Renewal Day", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitness_RenewalAfterMonth.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter  Fitness Renewal Month", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTaxCompanyName.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Tax Company Name", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTaxDate.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Tax Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTaxRenewalDate.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Tax Renewal Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTaxNo.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Tax Number", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTax_RenewalAfterDay.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Tax Renewal Day", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTax_RenewalAfterMonth.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Tax Renewal Month", Toast.LENGTH_SHORT).show()
            return false
        }else if (etpermitCompanyName.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Permit Company Name", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPermitDate.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Permit Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPermit_Renewal_Date.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Permit Renewal Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPermitNo.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Permit Number", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPermitRenewalAfterDay.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Permit Renewal Day", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPermit_RenewalAfterMonth.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Permit Renewal Month", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}