package com.frist.turkey.ui.Vehicle.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.frist.turkey.R
import com.frist.turkey.base.BaseFragment
import com.frist.turkey.model.MarketVehicle
import com.frist.turkey.utils.DatePickerHelper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.android.synthetic.main.fragment_market_vehicle.*
import java.util.*


class MarketVehicleFragment :BaseFragment(), View.OnClickListener {
    lateinit var datePicker: DatePickerHelper
    lateinit var databaseReference: DatabaseReference

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
        databaseReference = FirebaseDatabase.getInstance().reference
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
    private var marketVehicleId = ""
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

        marketVehicleId = marketVehicleId ?: databaseReference.child("Market Vehicle Detail")
            .push().key.toString()
        if ( validateMarketVehicle()){
            var marketVehicle= MarketVehicle(
                vehicletype = "Market Vehicle",
                date = date,
                GrNo = GrNo,
                EBillNo = EBillNo,
                VehicleOwner = VehicleOwner,
                MobileNumber = MobileNumber,
                AccountNumber = AccountNumber,
                IfscNumber = IfscNumber,
                TruckNumber = TruckNumber,
                Chaise_Number = Chaise_Number,
                DriverName = DriverName,
                DriverNo = DriverNo,
                ConsignorName = ConsignorName,
                ConsignorNo = ConsignorNo,
                ProductName = ProductName,
                fixedPerTonee = fixedPerTonee,
                RateWeight = RateWeight,
                ActualRate = ActualRate,
                RateGst = RateGst,
                Total = Total,
                RateGiven = RateGiven,
                Total_rateGiven = Total_RateGiven,
                Gst_RateGiven = Gst_RateGiven,
                //      Total_RateGiven = "8500",
                Commission_Charge = Commission_Charge,
                GuideCharge = GuideCharge,
                Advance_Amount = Advance_Amount,
                Advance_Amount_paid = Advance_Amount_paid,
                AdvancePercentageDeduction = AdvancePercentageDeduction,
                toatal_Advance_Amount_Paid = toatal_Advance_Amount_Paid,
                NooftimesDieselPaid = NooftimesDieselPaid,
                PumpName = PumpName,
                DieselRate = DieselRate,
                PerLiter = PerLiter,
                DieselAmount = DieselAmount,
                OtherExpenses = OtherExpenses,
                Remark = Remark,
                marketVehicleId = marketVehicleId,
                timeStamp =  ServerValue.TIMESTAMP
            )

            databaseReference.child("Market Vehicle Detail").child(marketVehicleId).setValue(marketVehicle)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(requireContext(), "set market vehicle data to firebase", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }else{

        }

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

    fun validateMarketVehicle():Boolean{
        if (etDate.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter date", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etGrNo.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Gr No", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etEBillNo.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter EBill No", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etVehicleOwner.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Vehicle Owner", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etMobileNumber.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etAccountNo.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Account No", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etIfscNumber.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter IFSC Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etTruckNumber.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Truck Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etChaise_Number.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Chaise Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etDriverName.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Driver Name", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etDriverNo.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Driver No", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etConsignorName.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter  Consignor Name", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etConsignorNo.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter  Consignor No", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etProductName.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Product Name", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etfixedPerTonee.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter weight in tone", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etRateWeight.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter rate of weight ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etActualRate.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter rate  ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etRateGst.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Gst  ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etTotal.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Total amount  ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etRateGiven.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter rate  ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etGst_RateGiven.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Gst  ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etTotal_RateGiven.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter total amount  ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etCommission_Charge.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Commission Charge  ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if ( etGuideCharge.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter Guide Charge ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (  etAdvance_Amount.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter  Advance Amount ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (  etAdvance_Amount_paid.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter  Advance Amount ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (  etAdvancePercentageDeduction.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter  Advance Percentage Deduction ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (  ettoatal_Advance_Amount_Paid.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter  Total Advance Percentage Deduction ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (  etNooftimesDieselPaid.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "No of times Diesel Paid ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etPumpName.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), " Pump Name ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (   etDieselRate.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), " Diesel Rate ", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etPerLiter.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "  Per Liter", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etDieselAmount.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "  Per Liter", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etOtherExpenses.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "  Other Expenses", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (etRemark.text.toString().isNullOrEmpty()){
            Toast.makeText(requireContext(), "Remark", Toast.LENGTH_SHORT).show()
            return false
        }
        return false
    }

}