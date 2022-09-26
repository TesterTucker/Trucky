package com.frist.turkey.ui.Vehicle.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.frist.turkey.R
import com.frist.turkey.base.BaseFragment
import com.frist.turkey.model.BrokerVehicle
import com.frist.turkey.model.OwnerVehicle
import com.frist.turkey.utils.DatePickerHelper
import com.frist.turkey.utils.shareDoucments
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.android.synthetic.main.dialog_market_vehicle.view.*
import kotlinx.android.synthetic.main.fragment_market_vehicle.*
import kotlinx.android.synthetic.main.fragment_own_vehicle.*
import java.security.acl.Owner
import java.util.*


class OwnVehicleFragment : BaseFragment(), View.OnClickListener {
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
        return inflater.inflate(R.layout.fragment_own_vehicle, container, false)
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
        etDate_Owner.setOnClickListener(this)
        btn_saveBrokerVehicle.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.etDate_Owner -> {
                showDatePickerDialog(1)
            }
            R.id.btn_saveBrokerVehicle -> {
                sendOwnerVehicleDataToFirebase(OwnerVehicle())
            }
        }
    }

    private var OwnerVehicleId = ""
    private fun sendOwnerVehicleDataToFirebase(isOwnerVehicleEdit: OwnerVehicle? = null) {
        val Date = etDate_Owner.text.toString().trim()
        val GrNo = etGrNo_Owner.text.toString().trim()
        val EBillNo = etEBillNo_Owner.text.toString().trim()
        val VehicleOwnerName = etVehicleOwner_Owner.text.toString().trim()
        val MobileNumber_Owner = etMobileNumber_Owner.text.toString().trim()
        val AccountNo_Owner = etAccountNo_Owner.text.toString().trim()
        val IfscNumber_Owner = etIfscNumber_Owner.text.toString().trim()
        val TruckNumber_Owner = etTruckNumber_Owner.text.toString().trim()
        val Chaise_Number_Owner = etChaise_Number_Owner.text.toString().trim()
        val DriverName_Owner = etDriverName_Owner.text.toString().trim()
        val DriverNo_Owner = etDriverNo_Owner.text.toString().trim()
        val ConsignorName_Owner = etConsignorName_Owner.text.toString().trim()
        val ConsignorNo_Owner = etConsignorNo_Owner.text.toString().trim()
        val ProductName_Owner = etProductName_Owner.text.toString().trim()
        val fixedPerTonee_Owner = etfixedPerTonee_Owner.text.toString().trim()
        val RateWeight_Owner = etRateWeight_Owner.text.toString().trim()
        val Total_Owner = etTotal_Owner.text.toString().trim()
        val Gst_Owner = etGst_Owner.text.toString().trim()
        val Total_OwnerDetail = etTotal_OwnerDetail.text.toString().trim()
        val PaidBy_Owner = etPaidBy_Owner.text.toString().trim()
        val PhoneNo_Paidby__Owner = etPhoneNo_Paidby__Owner.text.toString().trim()
        val startPlace_OwnerVehicle = etstartPlace_OwnerVehicle.text.toString().trim()
        val EndPlace_Owner = etEndPlace_Owner.text.toString().trim()

        OwnerVehicleId =isOwnerVehicleEdit?. OwnerVehicleId ?: databaseReference.child("Owner Vehicle Detail")
            .push().key.toString()
        val ownerVehicle = OwnerVehicle(
            vehicletype = "Owner Vehicle",
            date = Date,
            GrNo = GrNo,
            EBillNo = EBillNo,
            VehicleOwner = VehicleOwnerName,
            MobileNumber = MobileNumber_Owner,
            AccountNumber = AccountNo_Owner,
            IfscNumber = IfscNumber_Owner,
            TruckNumber = TruckNumber_Owner,
            Chaise_Number = Chaise_Number_Owner,
            DriverName = DriverName_Owner,
            DriverNo = DriverNo_Owner,
            ConsignorName = ConsignorName_Owner,
            ConsignorNo = ConsignorNo_Owner,
            ProductName = ProductName_Owner,
            fixedPerTonee = fixedPerTonee_Owner,
            RateWeight = RateWeight_Owner,
            ActualRate = Total_Owner,
            RateGst = Gst_Owner,
            Total = Total_OwnerDetail,
            PaidBy = PaidBy_Owner,
            PaidByNumber = PhoneNo_Paidby__Owner,
            OwnerVehicleId = OwnerVehicleId,
            timeStamp = ServerValue.TIMESTAMP,
            loadingPoint = startPlace_OwnerVehicle,
            unloadingPoint = EndPlace_Owner
        )
        if (validateOwnerVehicle()) {
            databaseReference.child("Owner Vehicle Detail").child(OwnerVehicleId)
                .setValue(ownerVehicle)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                          openDialogue(Date,TruckNumber_Owner,DriverName_Owner,DriverNo_Owner,ConsignorName_Owner,ConsignorNo_Owner,
                              fixedPerTonee_Owner,RateWeight_Owner,Gst_Owner,Total_OwnerDetail,startPlace_OwnerVehicle,EndPlace_Owner)
                          Toast.makeText(
                            requireContext(),
                            "set owner vehicle data to firebase",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }
    private fun openDialogue(date:String,TruckNumber:String,DriverName:String,DriverNo:String,ConsignorName:String,ConsignorNo:String,fixedPerTonee:String,RateWeight:String,RateGst:String,Total:String,startPlace:String,endPlace:String)
    {

        var view = LayoutInflater.from(context).inflate(R.layout.dialog_market_vehicle, null)
        var dialog = AlertDialog.Builder(context, 0).create()
        dialog.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        dialog.apply {
            setView(view)

            view.dateMarketVehicle.text="Date: ${date}"
            view.truckNumberMarketVehicle.text="Truck Number: ${TruckNumber}"
            view.DriverNameNumberMarketVehicle.text="Driver Name: ${DriverName}"
            view.DriverPhoneNoNumberMarketVehicle.text="Driver Number: ${DriverNo}"
            view.ConsignerNameMarketVehicle.text="Consigner Name: ${ConsignorName}"
            view.ConsignerNumberMarketVehicle.text="Consigner Number: ${ConsignorNo}"
            view.RateMarketVehicle.text="Consigner Number: ${fixedPerTonee}"
            view.GstMarketVehicle.text="GST: ${RateGst}"
            view.TotalAmountMarketVehicle.text="Total: ${Total}"
            view.loadingPointMarketVehicle.text="loadingPoint: ${startPlace}"
            view.unloadingPointMarketVehicle.text="unloadingPoint: ${endPlace}"
            view.editMarketVehicle.setOnClickListener {

                dialog.dismiss()
            }
            view.shareMarketVehicle.setOnClickListener {
                shareDoucments()
                dialog.dismiss()
            }

            setCancelable(false)
            setCanceledOnTouchOutside(true)

        }.show()



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
                        etDate_Owner.text = "${dayStr}-${monthStr}-${year}"
                    }


                }
            }
        })
    }

    fun validateOwnerVehicle(): Boolean {
        if ( etDate_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter date", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etEBillNo_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter E Bill No", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etVehicleOwner_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Owner Name", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etMobileNumber_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Mobile Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etAccountNo_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Account Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etIfscNumber_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter IFSC Code", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (   etTruckNumber_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Truck Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (    etChaise_Number_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Chaise Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etDriverName_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Driver Name", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etDriverNo_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Driver Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etConsignorName_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Consignor Name", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etConsignorNo_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Consignor Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etProductName_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Product Name", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etfixedPerTonee_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Fixed Per Tone", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etRateWeight_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Rate", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etTotal_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Total", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etGst_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Gst", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etTotal_OwnerDetail.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Total", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etPaidBy_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Paid By", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etPhoneNo_Paidby__Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Paid By Phone Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etstartPlace_OwnerVehicle.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Loading Place", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etEndPlace_Owner.text.toString().trim().isNullOrEmpty()){
            Toast.makeText(context, "Enter Unloading Place", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}