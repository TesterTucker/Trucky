package com.frist.turkey.ui.Vehicle.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frist.turkey.R
import com.frist.turkey.base.BaseFragment
import com.frist.turkey.model.MarketVehicle
import com.frist.turkey.utils.DatePickerHelper
import kotlinx.android.synthetic.main.fragment_market_vehicle.*
import java.util.*


class MarketVehicleFragment :BaseFragment(), View.OnClickListener {
    lateinit var datePicker: DatePickerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_market_vehicle, container, false)
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
        etDate.setOnClickListener(this)
        btn_saveMarketVehicle.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
          R.id.etDate->{
              showDatePickerDialog(1)
          }
            R.id.btn_saveMarketVehicle->{
              sendMarketVehicleDataToFirebase()
          }
        }
    }

    private fun sendMarketVehicleDataToFirebase() {
       val date= etDate.text.toString().trim()
       val GrNo= etGrNo.text.toString().trim()
       val EBillNo= etEBillNo.text.toString().trim()
       val VehicleOwner= etVehicleOwner.text.toString().trim()
       val MobileNumber= etMobileNumber.text.toString().trim()
       val AccountNumber= etAccountNo.text.toString().trim()
       val IfscNumber= etIfscNumber.text.toString().trim()
       val TruckNumber= etTruckNumber.text.toString().trim()
       val Chaise_Number= etChaise_Number.text.toString().trim()
       val DriverName= etDriverName.text.toString().trim()
       val DriverNo= etDriverNo.text.toString().trim()
       val ConsignorName= etConsignorName.text.toString().trim()
       val ConsignorNo= etConsignorNo.text.toString().trim()
       val ProductName= etProductName.text.toString().trim()
       val fixedPerTonee= etfixedPerTonee.text.toString().trim()
       val RateWeight= etRateWeight.text.toString().trim()
       val ActualRate= etActualRate.text.toString().trim()
       val RateGst= etRateGst.text.toString().trim()
       val Total= etTotal.text.toString().trim()
       val RateGiven= etRateGiven.text.toString().trim()
       val Total_rateGiven= etTotal_rateGiven.text.toString().trim()
       val Gst_RateGiven= etGst_RateGiven.text.toString().trim()
       val Total_RateGiven= etTotal_RateGiven.text.toString().trim()
       val Commission_Charge= etCommission_Charge.text.toString().trim()
       val GuideCharge= etGuideCharge.text.toString().trim()
       val Advance_Amount= etAdvance_Amount.text.toString().trim()
       val Advance_Amount_paid= etAdvance_Amount_paid.text.toString().trim()
       val AdvancePercentageDeduction= etAdvancePercentageDeduction.text.toString().trim()
       val toatal_Advance_Amount_Paid= ettoatal_Advance_Amount_Paid.text.toString().trim()
       val NooftimesDieselPaid= etNooftimesDieselPaid.text.toString().trim()
       val PumpName= etPumpName.text.toString().trim()
       val DieselRate= etDieselRate.text.toString().trim()
       val PerLiter= etPerLiter.text.toString().trim()
       val DieselAmount= etDieselAmount.text.toString().trim()
       val OtherExpenses= etOtherExpenses.text.toString().trim()
       val Remark= etRemark.text.toString().trim()

     //   var marketVehicle= MarketVehicle()

    }

    private fun showDatePickerDialog(type: Int) {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                when (type) {
                    1 -> {
                        etDate.text = "${dayStr}-${monthStr}-${year}"
                    }


                }
            }
        })
    }

}