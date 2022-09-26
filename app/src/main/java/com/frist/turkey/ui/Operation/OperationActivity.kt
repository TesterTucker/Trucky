package com.frist.turkey.ui.Operation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.frist.turkey.R
import com.frist.turkey.base.BaseActivity
import com.frist.turkey.model.BrokerVehicle
import com.frist.turkey.model.CommonVehicle
import com.frist.turkey.model.MarketVehicle
import com.frist.turkey.model.OwnerVehicle
import com.frist.turkey.ui.Vehicle.VehicleActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_operation.*

class OperationActivity : BaseActivity(), View.OnClickListener {
    lateinit var databaseReference: DatabaseReference
    var commonVehicleList: ArrayList<CommonVehicle> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)
        initViews()
        initControl()
    }

    override fun initViews() {
        rvOperation.adapter=OperationAdapter(arrayListOf(),object :OperationAdapter.CommonListener{
            override fun editData(item: CommonVehicle) {
                when(item.vehicletype){
                    "Broker Vehicle"->{
                       startActivity(Intent(this@OperationActivity,VehicleActivity::class.java).putExtra("Broker Vehicle","Broker Vehicle"))
                    }
                    "Market Vehicle"->{
                            startActivity(Intent(this@OperationActivity,VehicleActivity::class.java).putExtra("Market Vehicle","Market Vehicle"))

                    }
                    "Owner Vehicle"->{
                        startActivity(Intent(this@OperationActivity,VehicleActivity::class.java).putExtra("Owner Vehicle","Owner Vehicle"))

                    }
                }
            }

            override fun createbuiltyData(item: CommonVehicle) {

            }

        })
        databaseReference = FirebaseDatabase.getInstance().reference



        etSearchDriver.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty()) {
                    if (rvOperation?.adapter != null) {
                        (rvOperation?.adapter as OperationAdapter)?.vehicleList =
                            ArrayList(commonVehicleList.filter {
                                it.ConsignorName.toString().contains(p0.toString(), true)
                            })
                        (rvOperation?.adapter as OperationAdapter)?.notifyDataSetChanged()
                    }
                } else {
                    if (rvOperation?.adapter != null) {
                        (rvOperation?.adapter as OperationAdapter)?.vehicleList =
                            commonVehicleList
                        (rvOperation?.adapter as OperationAdapter)?.notifyDataSetChanged()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty()) {
                    if (rvOperation?.adapter != null) {
                        (rvOperation?.adapter as OperationAdapter)?.vehicleList =
                            ArrayList(commonVehicleList.filter {
                                it.ConsignorName.toString().contains(p0.toString(), true)
                            })
                        (rvOperation?.adapter as OperationAdapter)?.notifyDataSetChanged()
                    }
                } else {
                    if (rvOperation?.adapter != null) {
                        (rvOperation?.adapter as OperationAdapter)?.vehicleList =
                            commonVehicleList
                        (rvOperation?.adapter as OperationAdapter)?.notifyDataSetChanged()
                    }
                }


            }

        })

    }

    override fun initControl() {
        btnBookNew_Operation.setOnClickListener(this)
        SortOperation.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        commonVehicleList.clear()
        databaseReference.child("Owner Vehicle Detail")
            .addChildEventListener(OwnerVehicleEventListener)
        databaseReference.child("Market Vehicle Detail")
            .addChildEventListener(MarketVehicleEventListener)
        databaseReference.child("Broker Vehicle Detail")
            .addChildEventListener(BrokerVehicleEventListener)
    }

    //for fetch value from database for client
    private val OwnerVehicleEventListener = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(OwnerVehicle::class.java)
            Log.e("fireBase", "onChildMoved${data}")
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(OwnerVehicle::class.java)
            Log.e("fireBase", "onChildMoved${data}")
            data?.let {
                commonVehicleList?.add(
                    CommonVehicle(
                        vehicletype = data.vehicletype,
                        date = data.date,
                        GrNo = data.GrNo,
                        EBillNo = data.EBillNo,
                        VehicleOwner = data.VehicleOwner,
                        MobileNumber = data.MobileNumber,
                        AccountNumber = data.AccountNumber,
                        IfscNumber = data.IfscNumber,
                        TruckNumber = data.TruckNumber,
                        Chaise_Number = data.Chaise_Number,
                        DriverName = data.DriverName,
                        DriverNo = data.DriverNo,
                        ConsignorName = data.ConsignorName,
                        ConsignorNo = data.ConsignorNo,
                        ProductName = data.ProductName,
                        fixedPerTonee = data.fixedPerTonee,
                        RateWeight = data.RateWeight,
                        ActualRate = data.ActualRate,
                        RateGst = data.RateGst,
                        Total = data.Total,
                        PaidBy = data.PaidBy,
                        PaidByNumber = data.PaidByNumber,
                        OwnerVehicleId = data.OwnerVehicleId,
                        timeStamp = data.timeStamp,
                        loadingPoint = data.loadingPoint,
                        unloadingPoint = data.unloadingPoint,
                        StartPlace = data.loadingPoint,
                        EndPlace = data.unloadingPoint

                    )
                )
            }
            (rvOperation?.adapter as OperationAdapter)?.vehicleList = commonVehicleList
            (rvOperation?.adapter as OperationAdapter)?.notifyDataSetChanged()
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(OwnerVehicle::class.java)
            Log.e("fireBase", "onChildMoved${data}")


            Log.e("fireBase", "onChildMoved${data}")


        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }
    }

    //Market Vehicle
    private val MarketVehicleEventListener = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(MarketVehicle::class.java)
            Log.e("fireBase", "onChildMoved${data}")
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(MarketVehicle::class.java)
            Log.e("fireBase", "onChildMoved${data}")
            data?.let {
                commonVehicleList?.add(
                    CommonVehicle(
                        vehicletype = data.vehicletype,
                        date = data.date,
                        GrNo = data.GrNo,
                        EBillNo = data.EBillNo,
                        VehicleOwner = data.VehicleOwner,
                        MobileNumber = data.MobileNumber,
                        AccountNumber = data.AccountNumber,
                        IfscNumber = data.IfscNumber,
                        TruckNumber = data.TruckNumber,
                        Chaise_Number = data.Chaise_Number,
                        DriverName = data.DriverName,
                        DriverNo = data.DriverNo,
                        ConsignorName = data.ConsignorName,
                        ConsignorNo = data.ConsignorNo,
                        ProductName = data.ProductName,
                        fixedPerTonee = data.fixedPerTonee,
                        RateWeight = data.RateWeight,
                        ActualRate = data.ActualRate,
                        RateGst = data.RateGst,
                        Total = data.Total,

                        timeStamp = data.timeStamp,

                        RateGiven = data.RateGiven,
                        Total_rateGiven = data.Total_rateGiven,
                        Gst_RateGiven = data.Gst_RateGiven,
                        Commission_Charge = data.Commission_Charge,
                        GuideCharge = data.GuideCharge,
                        Advance_Amount = data.Advance_Amount,
                        Advance_Amount_paid = data.Advance_Amount_paid,
                        AdvancePercentageDeduction = data.AdvancePercentageDeduction,
                        toatal_Advance_Amount_Paid = data.toatal_Advance_Amount_Paid,
                        NooftimesDieselPaid = data.NooftimesDieselPaid,
                        PumpName = data.PumpName,
                        DieselRate = data.DieselRate,
                        PerLiter = data.PerLiter,
                        DieselAmount = data.DieselAmount,
                        OtherExpenses = data.OtherExpenses,
                        Remark = data.Remark,
                        marketVehicleId = data.marketVehicleId,


                    )
                )


            }
            (rvOperation?.adapter as OperationAdapter)?.vehicleList = commonVehicleList
            (rvOperation?.adapter as OperationAdapter)?.notifyDataSetChanged()

        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(MarketVehicle::class.java)
            Log.e("fireBase", "onChildMoved${data}")


            Log.e("fireBase", "onChildMoved${data}")

        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }
    }

    //Broker Vehicle listner
    private val BrokerVehicleEventListener = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(BrokerVehicle::class.java)
            Log.e("fireBase", "onChildMoved${data}")
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(BrokerVehicle::class.java)
            Log.e("fireBase", "onChildMoved${data}")
            data?.let {
                commonVehicleList?.add(
                    CommonVehicle(
                        vehicletype = data.vehicletype,
                        date = data.date,
                        EBillNo = data.EBillNo,
                        VehicleOwner = data.VehicleOwner,
                        MobileNumber = data.MobileNumber,
                        AccountNumber = data.AccountNumber,
                        IfscNumber = data.IfscNumber,
                        TruckNumber = data.TruckNumber,
                        Chaise_Number = data.Chaise_Number,
                        DriverName = data.DriverName,
                        DriverNo = data.DriverNo,
                        ConsignorName = data.ConsignorName,
                        ConsignorNo = data.ConsignorNo,
                        ProductName = data.ProductName,
                        fixedPerTonee = data.fixedPerTonee,
                        RateWeight = data.RateWeight,
                        ActualRate = data.ActualRate,
                        RateGst = data.RateGst,
                        Total = data.Total,
                        PaidBy = data.PaidBy,
                        PaidByNumber = data.PaidByNumber,

                        timeStamp = data.timeStamp,
                        loadingPoint = data.loadingPoint,
                        unloadingPoint = data.unloadingPoint,
                        BrokerName = data.BrokerName,
                        BrokerVehicleId = data.BrokerVehicleId,
                        StartPlace = data.loadingPoint,
                        EndPlace = data.unloadingPoint

                    )
                )


            }
            (rvOperation?.adapter as OperationAdapter)?.vehicleList = commonVehicleList
            (rvOperation?.adapter as OperationAdapter)?.notifyDataSetChanged()

        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(BrokerVehicle::class.java)
            Log.e("fireBase", "onChildMoved${data}")


            Log.e("fireBase", "onChildMoved${data}")

        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }
    }

    /* private fun openBottomSheet() {

        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        bottomSheetDialog?.setContentView(R.layout.sort_bottom_sheet_dialog)
        bottomSheetDialog?.A_ZSort?.setOnClickListener {
            clientList.sortBy { it.clientName }//a-z
            (rvClientDetail?.adapter as ClientDetailAdapter)?.notifyDataSetChanged()
            bottomSheetDialog?.dismiss()
        }
        bottomSheetDialog?.Z_ASort?.setOnClickListener {
            clientList.sortByDescending  { it.clientName}//a-z
            (rvClientDetail?.adapter as ClientDetailAdapter)?.notifyDataSetChanged()
            bottomSheetDialog?.dismiss()
        }
        bottomSheetDialog?.New_OldSort?.setOnClickListener {
            clientList.sortBy { (it.timeStamp as Long) }//new
            (rvClientDetail?.adapter as ClientDetailAdapter)?.notifyDataSetChanged()
            bottomSheetDialog?.dismiss()
        }
        bottomSheetDialog?.Old_NewSort?.setOnClickListener {
            clientList.sortByDescending { (it.timeStamp as Long) }//old
            (rvClientDetail?.adapter as ClientDetailAdapter)?.notifyDataSetChanged()
            bottomSheetDialog?.dismiss()
        }
        bottomSheetDialog?.show()
    }*/
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnBookNew_Operation -> {
                startActivity(Intent(this, VehicleActivity::class.java))

            }
            R.id.SortOperation -> {
                // openBottomSheet()
            }
        }
    }
}