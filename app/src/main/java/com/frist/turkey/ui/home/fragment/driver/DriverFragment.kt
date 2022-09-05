package com.frist.turkey.ui.home.fragment.driver

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.frist.turkey.R
import com.frist.turkey.base.BaseFragment
import com.frist.turkey.model.Driver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_driver.*


class DriverFragment : BaseFragment(), View.OnClickListener {
        lateinit var auth:FirebaseAuth
        lateinit var databaseReference: DatabaseReference
        lateinit var firebaseStorage: FirebaseStorage
        private  var curentUser:String?=null
    var ImageUri:Uri?=null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initControl()

    }

  override   fun initViews() {
        auth= FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().reference

      curentUser= auth.uid


    }

    override fun initControl() {
        btn_saveDriverDetail.setOnClickListener(this)
        uplaodlicense.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver, container, false)
    }


    companion object {
        private const val EXTRA_TEXT = "text"
        fun createFor(text: String?): DriverFragment {
            val fragment = DriverFragment()
            val args = Bundle()
            args.putString(EXTRA_TEXT, text)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_saveDriverDetail->{
                sendDriverDataToFirebase()
            }
            R.id.uplaodlicense->{
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, 100)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ImageUri = data?.data
            tvLicense.setImageURI(ImageUri)
        }
    }


    private fun sendDriverDataToFirebase() {
        Toast.makeText(context, "Driver Name Set on Database cliked", Toast.LENGTH_SHORT).show()
        val driverName=etDriverName.text.toString().trim()
        val driverPhoneNumbe=etDriverPhoneNumbe.text.toString().trim()
        val driverLicenseNo=etDriverLicenseNo.text.toString().trim()
        val driverAadhaarNo=etDriverAadhaarNo.text.toString().trim()
        val driverAddress=etDriverAddress.text.toString().trim()

        val driver=Driver(driverName,driverPhoneNumbe,driverLicenseNo,driverAadhaarNo,driverAddress)

        if (driverValidation()){
            if (curentUser !=null){
                databaseReference.child(curentUser!!).child("DriverDetail").push().setValue(driver).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(context, "Driver Name Set on Database", Toast.LENGTH_SHORT).show()
                        sendDriverImageToFirebase()

                    }else{
                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }

    private fun sendDriverImageToFirebase() {
        if (ImageUri != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            // Defining the child of storageReference
            firebaseStorage = FirebaseStorage.getInstance()
            var  storageReference = firebaseStorage.getReference()
                .child(
                    "images/"
                            + curentUser.toString()
                )

            // adding listeners on upload
            // or failure of image
            storageReference.putFile(ImageUri!!)
                .addOnSuccessListener { // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                           "Data send to firebase" /*"Image Uploaded!!"*/,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .addOnFailureListener { e -> // Error, Image not uploaded
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Failed " + e.message,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->

                    // Progress Listener for loading
                    // percentage on the dialog box
                    val progress = (100.0
                            * taskSnapshot.bytesTransferred
                            / taskSnapshot.totalByteCount)
                    progressDialog.setMessage(
                        "Uploaded "
                                + progress.toInt() + "%"
                    )
                }
        }
    }

    fun driverValidation():Boolean{
        if (etDriverName.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Driver Name", Toast.LENGTH_SHORT).show()
            return false
        }else if(etDriverPhoneNumbe.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Driver Mobile Number", Toast.LENGTH_SHORT).show()
            return false
        }else if(etDriverLicenseNo.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Driver License No", Toast.LENGTH_SHORT).show()
            return false
        }else if(etDriverAadhaarNo.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Driver Aadhaar No", Toast.LENGTH_SHORT).show()
            return false
        }else if(etDriverAddress.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Enter Driver Address", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


}