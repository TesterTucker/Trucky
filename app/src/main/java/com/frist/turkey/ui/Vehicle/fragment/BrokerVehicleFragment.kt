package com.frist.turkey.ui.Vehicle.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.frist.turkey.R
import com.frist.turkey.base.BaseFragment
import com.frist.turkey.model.BrokerVehicle
import com.frist.turkey.utils.DatePickerHelper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.android.synthetic.main.fragment_broker_vehicle.*
import kotlinx.android.synthetic.main.fragment_market_vehicle.*
import java.util.*


class BrokerVehicleFragment : BaseFragment(), View.OnClickListener {
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
        return inflater.inflate(R.layout.fragment_broker_vehicle, container, false)
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
        etDateBrokerVehicle.setOnClickListener(this)
        btn_saveBrokerVehicle.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.etDateBrokerVehicle -> {
                showDatePickerDialog(1)
            }
            R.id.btn_saveBrokerVehicle -> {
                sendBrokerVehicleDataToFirebase()
            }
        }
    }

    private var BrokerVehicleId = ""
    private fun sendBrokerVehicleDataToFirebase() {
        val date = etDateBrokerVehicle.text.toString().trim()
        val brokerName = etBrokerName.text.toString().trim()
        val EBillNo_BrokerVehicle = etEBillNo_BrokerVehicle.text.toString().trim()
        val VehicleOwner_Broker = etVehicleOwner_Broker.text.toString().trim()
        val MobileNumber_Broker_Owner = etMobileNumber_Broker.text.toString().trim()
        val AccountNo_Broker = etAccountNo_Broker.text.toString().trim()
        val IfscNumber_Broker = etIfscNumber_Broker.text.toString().trim()
        val TruckNumber_BrokeVehicle = etTruckNumber_BrokeVehicle.text.toString().trim()
        val Chaise_Number = etChaise_Number_Broker.text.toString().trim()
        val DriverName_BrokerVehicle = etDriverName_BrokerVehicle.text.toString().trim()
        val DriverNoBrokerVehicle = etDriverNoBrokerVehicle.text.toString().trim()
        val ConsignorName_BrokerVehicle = etConsignorName_BrokerVehicle.text.toString().trim()
        val ConsignorNo_BrokerVehicle = etConsignorNo_BrokerVehicle.text.toString().trim()
        val ProductName_BrokerVehicle = etProductName_BrokerVehicle.text.toString().trim()
        val fixedPerTonee_BrokerVehicle = etfixedPerTonee_BrokerVehicle.text.toString().trim()
        val RateWeight_BrokerVehicle = etRateWeight_BrokerVehicle.text.toString().trim()
        val etTotal_rateGiven = etTotal_rateGiven_Broker.text.toString().trim()
        val Gst_BrokerVehicle = etGst_BrokerVehicle.text.toString().trim()
        val Total_BrokerVehicle = etTotal_BrokerVehicle.text.toString().trim()
        val PaidBy_BrokerVehicle = etPaidBy_BrokerVehicle.text.toString().trim()
        val PhoneNo_Paidby_BrokerVehicle = etPhoneNo_Paidby_BrokerVehicle.text.toString().trim()

        var brokerVehicle = BrokerVehicle(
            vehicletype = "Broker Vehicle",
            date = date,
            BrokerName = brokerName,
            EBillNo = EBillNo_BrokerVehicle,
            VehicleOwner = VehicleOwner_Broker,
            MobileNumber = MobileNumber_Broker_Owner,
            AccountNumber = AccountNo_Broker,
            IfscNumber = IfscNumber_Broker,
            TruckNumber = TruckNumber_BrokeVehicle,
            Chaise_Number = Chaise_Number,
            DriverName = DriverName_BrokerVehicle,
            DriverNo = DriverNoBrokerVehicle,
            ConsignorName = ConsignorName_BrokerVehicle,
            ConsignorNo = ConsignorNo_BrokerVehicle,
            ProductName = ProductName_BrokerVehicle,
            fixedPerTonee = fixedPerTonee_BrokerVehicle,
            RateWeight = RateWeight_BrokerVehicle,
            ActualRate = etTotal_rateGiven,
            RateGst = Gst_BrokerVehicle,
            Total = Total_BrokerVehicle,
            PaidBy = PaidBy_BrokerVehicle,
            PaidByNumber = PhoneNo_Paidby_BrokerVehicle,
            BrokerVehicleId = BrokerVehicleId,
            timeStamp = ServerValue.TIMESTAMP
        )

        if (validateBrokerVehicle()){
            databaseReference.child("Broker Vehicle Detail").child(BrokerVehicleId)
                .setValue(brokerVehicle)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "set broker vehicle data to firebase",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                    }
                }

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
                        etDateBrokerVehicle.text = "${dayStr}-${monthStr}-${year}"
                    }


                }
            }
        })
    }

    fun validateBrokerVehicle(): Boolean {
        if (etDateBrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Date", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etBrokerName.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Broker Name", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etEBillNo_BrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter E Bill No", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etVehicleOwner_Broker.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Vehicle Owner", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etMobileNumber_Broker.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (   etAccountNo_Broker.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Account Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etIfscNumber_Broker.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter IFSC Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etTruckNumber_BrokeVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Truck Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (etChaise_Number_Broker.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Chaise Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etDriverName_BrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Driver Name", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etDriverNoBrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Driver Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etConsignorName_BrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Consignor Name", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etConsignorNo_BrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Consignor Number", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etProductName_BrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Product Name", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (   etfixedPerTonee_BrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Fixed Per Tone", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (   etRateWeight_BrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Rate Weight", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etTotal_rateGiven_Broker.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Total", Toast.LENGTH_SHORT).show()
            return false
        }
        else if ( etGst_BrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Gst", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etTotal_BrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Total", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etPaidBy_BrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Paid By", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (  etPhoneNo_Paidby_BrokerVehicle.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Paid By Number", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}