package com.frist.turkey.ui.home.fragment.driver

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
import com.frist.turkey.model.TyreDetail
import com.frist.turkey.ui.home.fragment.TyreDetail.TyreDetailFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_search_driver_detail.*
import kotlinx.android.synthetic.main.sort_bottom_sheet_dialog.*


class SearchDriverDetailFragment : BaseFragment(), View.OnClickListener {
    lateinit var databaseReference: DatabaseReference
    var driverList: ArrayList<Driver> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_driver_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initControl()
    }

    override fun initViews() {
        databaseReference = FirebaseDatabase.getInstance().reference
    }

    override fun onResume() {
        super.onResume()
        driverList.clear()
        databaseReference.child("DriverDetail").addChildEventListener(childEventListener)
    }

    override fun initControl() {
        btnAddDriverDetail.setOnClickListener(this)
        tvSearch.setOnClickListener(this)
        SortDriverDetail.setOnClickListener(this)
        etSearchDriver.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty()) {
                    if (rvDriverDetail?.adapter != null) {
                        (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.driverList =
                            ArrayList(driverList.filter {
                                it.driverName.toString().contains(p0.toString(), true)
                            })
                        (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.notifyDataSetChanged()
                    }
                } else {
                    if (rvDriverDetail?.adapter != null) {
                        (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.driverList =
                            driverList
                        (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.notifyDataSetChanged()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty()) {
                    if (rvDriverDetail?.adapter != null) {
                        (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.driverList =
                            ArrayList(driverList.filter {
                                it.driverName.toString().contains(p0.toString(), true)
                            })
                        (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.notifyDataSetChanged()
                    }
                } else {
                    if (rvDriverDetail?.adapter != null) {
                        (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.driverList =
                            driverList
                        (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.notifyDataSetChanged()
                    }
                }
            }
        })
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnAddDriverDetail -> {
                startActivity(Intent(requireContext(), DriverActivity::class.java))
            }
            R.id.tvSearch -> {

            }
            R.id.SortDriverDetail -> {
                openBottomSheet()
            }
        }
    }

    private fun openBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.sort_bottom_sheet_dialog, null)
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    //for fetch value from database
    private val childEventListener = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(Driver::class.java)
            Log.e("fireBase", "onChildMoved${data}")
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(Driver::class.java)
            Log.e("fireBase", "onChildMoved${data}")
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(Driver::class.java)
            Log.e("fireBase", "onChildMoved${data}")
            // etSearchDriver.setText(data?.driverLicenseNo)
            driverList?.add(
                Driver(
                    driverName = data?.driverName,
                    driverMobileNumber = data?.driverMobileNumber,
                    driverLicenseNo = data?.driverLicenseNo,
                    DriverAadhaarNo = data?.DriverAadhaarNo,
                    driverAddress = data?.driverAddress,
                    driverDetailsId = data?.driverDetailsId,
                    timeStamp = data?.timeStamp ?: 0
                )
            )
         /*   driverList.sortBy { it.driverName }//a-z
            (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.notifyDataSetChanged()
            driverList.sortByDescending { it.driverName }//z-a
            (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.notifyDataSetChanged()
            driverList.sortBy { (it.timeStamp as Long) }//new
            (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.notifyDataSetChanged()
            driverList.sortByDescending { (it.timeStamp as Long) }//old
            (rvDriverDetail?.adapter as SearchDriverDetailAdapter)?.notifyDataSetChanged()*/

            Log.e("fireBase", "onChildMoved${data}")
            this@SearchDriverDetailFragment.context?.let {mContext->
                rvDriverDetail.adapter = driverList.let {
                    SearchDriverDetailAdapter(
                        mContext,
                        it, object : SearchDriverDetailAdapter.EditDriver {
                            override fun onClickEditDriver(driverModel: Driver) {
                                startActivity(
                                    Intent(requireContext(), DriverActivity::class.java)
                                        .putExtra("isEdit", driverModel)
                                )
                            }

                        }
                    )
                }
            }
        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }
    }

    companion object {
        private const val EXTRA_TEXT = "text"
        fun createFor(text: String?): SearchDriverDetailFragment {
            val fragment = SearchDriverDetailFragment()
            val args = Bundle()
            args.putString(EXTRA_TEXT, text)
            fragment.setArguments(args)
            return fragment
        }
    }


}