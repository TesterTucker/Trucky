package com.frist.turkey.ui.home.fragment.truckDetail


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.frist.turkey.R
import com.frist.turkey.base.BaseFragment
import com.frist.turkey.model.Driver
import com.frist.turkey.model.TruckDetail
import com.frist.turkey.ui.home.HomeActivity
import com.frist.turkey.ui.home.fragment.HomeFragment
import com.frist.turkey.ui.home.fragment.driver.DriverActivity
import com.frist.turkey.utils.DatePickerHelper
import com.frist.turkey.utils.statusBarTransparent
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.Context
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_truck_detail.*
import kotlinx.android.synthetic.main.sort_bottom_sheet_dialog.*
import java.util.*

class TruckDetailFragment : BaseFragment() {
    var truckDetailList:ArrayList<TruckDetail>?= arrayListOf()
    lateinit var databaseReference: DatabaseReference
    var bottomSheetDialog: BottomSheetDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_truck_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        statusBarTransparent()
        initViews()
        initControl()


    }
    override fun initViews() {
        databaseReference=FirebaseDatabase.getInstance().reference
        SortTruckDetail.setOnClickListener {
            openBottomSheet()
        }
        btnAddTruckDetail.setOnClickListener {
            startActivity(Intent(requireContext(),SearchDetailActivity::class.java))
        }
        etSearchDriver.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty()) {
                    (rvTruckDetail.adapter as TruckDetailAdapter).truckDetailList =
                        ArrayList(truckDetailList?.filter {
                            it.driverName.toString().contains(p0.toString(), true)
                        })
                    (rvTruckDetail.adapter as TruckDetailAdapter).notifyDataSetChanged()
                } else {
                    (rvTruckDetail.adapter as TruckDetailAdapter).truckDetailList = truckDetailList!!
                    (rvTruckDetail.adapter as TruckDetailAdapter).notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty()) {
                    (rvTruckDetail.adapter as TruckDetailAdapter).truckDetailList =
                        ArrayList(truckDetailList?.filter {
                            it.driverName.toString().contains(p0.toString(), true)
                        })
                    (rvTruckDetail.adapter as TruckDetailAdapter).notifyDataSetChanged()
                } else {
                    (rvTruckDetail.adapter as TruckDetailAdapter).truckDetailList = truckDetailList!!
                    (rvTruckDetail.adapter as TruckDetailAdapter).notifyDataSetChanged()
                }

            }

        })

    }

    override fun initControl() {

    }
    //for fetch value from database
    private val childEventListener = object : ChildEventListener {

        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(TruckDetail::class.java)
            Log.e("fireBase", "onChildMoved${data}")


        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(TruckDetail::class.java)
            Log.e("fireBase", "onChildMoved${data}")




        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(TruckDetail::class.java)
            Log.e("fireBase", "onChildMoved${data}")
            // etSearchDriver.setText(data?.driverLicenseNo)
            truckDetailList?.add(
                TruckDetail(
                    driverName = data?.driverName,
                    truckBrand = data?.truckBrand,
                    truckRate = data?.truckRate,
                    DelearPurchaseDate = data?.DelearPurchaseDate,
                    truckNumber = data?.truckNumber,
                    truckPurchaseDate = data?.truckPurchaseDate,
                    truckChaiseNumber = data?.truckChaiseNumber,
                    truckNoOfTyre = data?.truckNoOfTyre,
                    InsurenceCompanyName = data?.InsurenceCompanyName,
                    InsurenceDate = data?.InsurenceDate,
                    InsurenceRenewalDate = data?.InsurenceRenewalDate,
                    InsurenceRenewalDay = data?.InsurenceRenewalDay,
                    InsurenceRenewalMonth = data?.InsurenceRenewalMonth,
                    InsurenceNo = data?.InsurenceNo,
                    FitnessCompanyName = data?.FitnessCompanyName,
                    FitnessDate = data?.FitnessDate,
                    FitnessRenewalDate = data?.FitnessRenewalDate,
                    FitnessNumber = data?.FitnessNumber,
                    FitnessRenewalDays = data?.FitnessRenewalDays,
                    FitnessRenewalMonth = data?.FitnessRenewalMonth,
                    TaxCompanyName = data?.TaxCompanyName,
                    TaxDate = data?.TaxDate,
                    TaxRenewalDate = data?.TaxRenewalDate,
                    TaxNumber = data?.TaxNumber,
                    TaxDay = data?.TaxDay,
                    TaxMonth = data?.TaxMonth,
                    PermitCompanyname = data?.PermitCompanyname,
                    PermitDate = data?.PermitDate,
                    PermitRenwalDate = data?.PermitRenwalDate,
                    PermitNumber = data?.PermitNumber,
                    PermitRenwalDay = data?.PermitRenwalDay,
                    PermitRenwalMonth = data?.PermitRenwalMonth,
                    truckDetailsId = data?.truckDetailsId,
                    timeStamp = data?.timeStamp,
                    truckModel = data?.truckModel,


                ))
            Log.e("fireBase", "onChildMoved${data}")

            rvTruckDetail.adapter= truckDetailList?.let {
                TruckDetailAdapter(requireContext(),
                    it,
                    object :TruckDetailAdapter.EditTruckDetail{
                        override fun onClickEditTruck(TruckModel: TruckDetail) {
                            startActivity(
                                Intent(requireContext(), SearchDetailActivity::class.java)
                                    .putExtra("isEditFromTruck", TruckModel)
                            )
                        }

                    }
                )
            }


        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }
    }
    private fun openBottomSheet() {

        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        bottomSheetDialog?.setContentView(R.layout.sort_bottom_sheet_dialog)
        bottomSheetDialog?.A_ZSort?.setOnClickListener {
            truckDetailList?.sortBy { it.driverName }//a-z
            (rvTruckDetail?.adapter as TruckDetailAdapter)?.notifyDataSetChanged()
            bottomSheetDialog?.dismiss()
        }
        bottomSheetDialog?.Z_ASort?.setOnClickListener {
            truckDetailList?.sortByDescending  { it.driverName }//a-z
            (rvTruckDetail?.adapter as TruckDetailAdapter)?.notifyDataSetChanged()
            bottomSheetDialog?.dismiss()
        }
        bottomSheetDialog?.New_OldSort?.setOnClickListener {
            truckDetailList?.sortBy { (it.timeStamp as Long) }//new
            (rvTruckDetail?.adapter as TruckDetailAdapter)?.notifyDataSetChanged()
            bottomSheetDialog?.dismiss()
        }
        bottomSheetDialog?.Old_NewSort?.setOnClickListener {
            truckDetailList?.sortByDescending { (it.timeStamp as Long) }//old
            (rvTruckDetail?.adapter as TruckDetailAdapter)?.notifyDataSetChanged()
            bottomSheetDialog?.dismiss()
        }
        bottomSheetDialog?.show()
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        truckDetailList?.clear()
        databaseReference.child("Truck Detail").addChildEventListener(childEventListener)
    }


}