package com.frist.turkey.ui.home.fragment.ClientDetailFragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.frist.turkey.R
import com.frist.turkey.base.BaseFragment
import com.frist.turkey.model.ClientDetail
import com.frist.turkey.model.Driver
import com.frist.turkey.ui.home.fragment.TyreDetail.TyreDetailFragment
import com.frist.turkey.ui.home.fragment.driver.DriverActivity
import com.frist.turkey.ui.home.fragment.driver.SearchDriverDetailAdapter
import com.frist.turkey.utils.SpinnerCallback
import com.frist.turkey.utils.spinnerMonth
import com.frist.turkey.utils.statusBarTransparent
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_client_detail.*
import kotlinx.android.synthetic.main.fragment_search_driver_detail.*
import kotlinx.android.synthetic.main.sort_bottom_sheet_dialog.*


class ClientDetailFragment : BaseFragment(){
    lateinit var databaseReference: DatabaseReference
    var clientList: ArrayList<ClientDetail> = arrayListOf()
    var bottomSheetDialog: BottomSheetDialog? = null
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
        statusBarTransparent()
        initViews()
        initControl()

    }

    override fun initViews() {
     databaseReference=FirebaseDatabase.getInstance().reference
        SortClientDetail.setOnClickListener {
            openBottomSheet()
        }

        etSearchClient.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty()) {
                    if (rvClientDetail?.adapter != null) {
                        (rvClientDetail?.adapter as ClientDetailAdapter)?.clientList =
                            ArrayList(clientList.filter {
                                it.clientName.toString().contains(p0.toString(), true)
                            })
                        (rvClientDetail?.adapter as ClientDetailAdapter)?.notifyDataSetChanged()
                    }
                } else {
                    if (rvClientDetail?.adapter != null) {
                        (rvClientDetail?.adapter as ClientDetailAdapter)?.clientList =
                            clientList
                        (rvClientDetail?.adapter as ClientDetailAdapter)?.notifyDataSetChanged()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty()) {
                    if (rvClientDetail?.adapter != null) {
                        (rvClientDetail?.adapter as ClientDetailAdapter)?.clientList =
                            ArrayList(clientList.filter {
                                it.clientName.toString().contains(p0.toString(), true)
                            })
                        (rvClientDetail?.adapter as ClientDetailAdapter)?.notifyDataSetChanged()
                    }
                } else {
                    if (rvClientDetail?.adapter != null) {
                        (rvClientDetail?.adapter as ClientDetailAdapter)?.clientList =
                            clientList
                        (rvClientDetail?.adapter as ClientDetailAdapter)?.notifyDataSetChanged()
                    }
                }
            }
        })

        btnAddClientDetail.setOnClickListener {
            startActivity(Intent(context,AddClientActivity::class.java))
        }
    }

    private fun openBottomSheet() {

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
    }


    override fun initControl() {

    }

    override fun onResume() {
        super.onResume()
        clientList.clear()
        databaseReference.child("Client Detail").addChildEventListener(childEventListener)
    }



    //for fetch value from database for client
    private val childEventListener = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(ClientDetail::class.java)
            Log.e("fireBase", "onChildMoved${data}")
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(ClientDetail::class.java)
            Log.e("fireBase", "onChildMoved${data}")
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(ClientDetail::class.java)
            Log.e("fireBase", "onChildMoved${data}")
            // etSearchDriver.setText(data?.driverLicenseNo)
            clientList?.add(ClientDetail(
                clientName = data?.clientName,
                typefClient = data?.typefClient,
                ClientMobileNumber = data?.ClientMobileNumber,
                ClientCompany = data?.ClientCompany,
                ClientAddress = data?.ClientAddress,
                clientDetailsId = data?.clientDetailsId,
                timeStamp = data?.timeStamp ?: 0,
            )
            )


            Log.e("fireBase", "onChildMoved${data}")
            this@ClientDetailFragment.context?.let {mContext->
                rvClientDetail.adapter = clientList.let {
                    ClientDetailAdapter(
                        mContext,
                        it, object : ClientDetailAdapter.EditClient{
                            override fun onClickEditDriver(clientModel: ClientDetail) {
                               startActivity(Intent(requireContext(), AddClientActivity::class.java)
                                    .putExtra("isClientEdit", clientModel))

                            }

                        })
                }
            }
        }

        override fun onChildRemoved(p0: DataSnapshot) {

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



}