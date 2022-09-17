package com.frist.turkey.ui.home.fragment.TyreDetail

import android.os.Bundle
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
import com.frist.turkey.ui.home.HomeActivity
import com.frist.turkey.utils.statusBarTransparent

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_tyre_detail.*


class TyreDetailFragment : BaseFragment(), View.OnClickListener {

    lateinit var databaseReference: DatabaseReference
    private var currentUser: String? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tyre_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        statusBarTransparent()
        initViews()
        initControl()
    }

    override fun initViews() {
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser.toString()
        databaseReference.child("Tyre Detail").addChildEventListener(childEventListener)
    }
    

    //for fetch value from database
    private val childEventListener = object : ChildEventListener {

        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(TyreDetail::class.java)
            Log.e("fireBase", "onChildMoved${data}")


        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(TyreDetail::class.java)
            Log.e("fireBase", "onChildMoved${data}")

        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val data = p0.getValue(TyreDetail::class.java)
            Log.e("fireBase", "onChildMoved${data}")
           // etTyre_TrucK_No.setText(data?.NoOfTyre)



        }

        override fun onChildRemoved(p0: DataSnapshot) {

        }
    }
    override fun initControl() {
        btn_saveTyreDetail.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_saveTyreDetail -> {
                if (validateTyreDetail()) {
                    Toast.makeText(
                        requireContext(),
                        "Tyre Detail Validation Done",
                        Toast.LENGTH_SHORT
                    ).show()
                    sendTyreDetailToFirebase()
                }
            }
        }
    }

    private fun sendTyreDetailToFirebase() {

        val truckNumber = etTyre_TrucK_No.text.toString()
        val tyreBrand = etTyreBrand.text.toString()
        val noOfTyre = etTyreDetailNoOfTyres.text.toString()
        val tyreDetail =
            TyreDetail(tyreTruckNumber = truckNumber, tyreBrand = tyreBrand, NoOfTyre = noOfTyre)

        if (currentUser != null) {
            databaseReference.child("Tyre Detail").push().setValue(tyreDetail)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Truck Detail Set on Database", Toast.LENGTH_SHORT)
                            .show()
                        (activity as HomeActivity).pbHome.visibility = View.GONE


                    } else {
                        (activity as HomeActivity).pbHome.visibility = View.GONE
                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()

                    }
                }

        }

    }

    fun recivedataFromFirebase() {

    }

    private fun validateTyreDetail(): Boolean {
        if (etTyre_TrucK_No.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Truck Number", Toast.LENGTH_SHORT).show()
            return false
        } else if (etTyreDetailNoOfTyres.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Number of Tyre", Toast.LENGTH_SHORT).show()
            return false
        } else if (etTyreBrand.text.toString().isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Enter Tyre Brand", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    companion object {
        private const val EXTRA_TEXT = "text"
        fun createFor(text: String?): TyreDetailFragment {
            val fragment = TyreDetailFragment()
            val args = Bundle()
            args.putString(EXTRA_TEXT, text)
            fragment.setArguments(args)
            return fragment
        }
    }

}