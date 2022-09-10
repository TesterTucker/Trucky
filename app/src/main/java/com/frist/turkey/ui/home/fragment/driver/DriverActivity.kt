package com.frist.turkey.ui.home.fragment.driver

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.frist.turkey.R
import com.frist.turkey.base.BaseActivity
import com.frist.turkey.model.Driver
import com.frist.turkey.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_driver.*

class DriverActivity : BaseActivity(), View.OnClickListener {

    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var firebaseStorage: FirebaseStorage
    private var curentUser: String? = null
    var ImageUri: Uri? = null
    var ImageUri1: Uri? = null
    var type: Int = -1
    private val driverModel: Driver?
        get() = intent.getParcelableExtra("isEdit") ?: null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_driver)
        initViews()
        initControl()
    }

    override fun initViews() {
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        curentUser = auth.uid
        sharedPreferences.currentUser = auth.uid.toString()
        driverModel?.let {
            etDriverName.setText(it.driverName)
            etDriverPhoneNumbe.setText(it.driverMobileNumber)
            etDriverLicenseNo.setText(it.driverLicenseNo)
            etDriverAadhaarNo.setText(it.DriverAadhaarNo)
            etDriverAddress.setText(it.driverAddress)
        }
    }

    override fun initControl() {
        btn_saveDriverDetail.setOnClickListener(this)
        uplaodlicense.setOnClickListener(this)
        tvuplaodAdharCard.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_saveDriverDetail -> {
                sendDriverDataToFirebase(driverModel)
            }
            R.id.uplaodlicense -> {
                type = 1
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, 100)
            }
            R.id.tvuplaodAdharCard -> {
                type = 2
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, 100)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            when (type) {
                1 -> {
                    ImageUri = data?.data
                    tvLicense.setImageURI(ImageUri)
                }
                2 -> {
                    ImageUri1 = data?.data
                    IvAdharCard.setImageURI(ImageUri1)
                }
            }
        }
    }

    private var driverDetailsId = ""
    private fun sendDriverDataToFirebase(isEditDriver: Driver? = null) {

        val driverName = etDriverName.text.toString().trim()
        val driverPhoneNumbe = etDriverPhoneNumbe.text.toString().trim()
        val driverLicenseNo = etDriverLicenseNo.text.toString().trim()
        val driverAadhaarNo = etDriverAadhaarNo.text.toString().trim()
        val driverAddress = etDriverAddress.text.toString().trim()
        driverDetailsId = isEditDriver?.driverDetailsId ?: databaseReference.child("DriverDetail").push().key.toString()
        val driver =
            Driver(
                driverName,
                driverPhoneNumbe,
                driverLicenseNo,
                driverAadhaarNo,
                driverAddress,
                driverDetailsId,
                ServerValue.TIMESTAMP
            )
        if (driverValidation()) {
            if (curentUser != null) {
                databaseReference.child("DriverDetail").child(driverDetailsId).setValue(driver)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Driver Name Set on Database",
                                Toast.LENGTH_SHORT
                            ).show()
                            ImageUri?.let { it1 ->
                                sendDriverImageToFirebase(
                                    imageUri = it1,
                                    imageName = "Driving License"
                                )
                            }
                            ImageUri1?.let { it1 ->
                                sendDriverImageToFirebase(
                                    imageUri = it1,
                                    imageName = "Aadhaar Card"
                                )
                            }
                        } else {

                            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()

                        }
                    }
            }
        }
    }

    private fun sendDriverImageToFirebase(imageUri: Uri, imageName: String) {
        // Code for showing progressDialog while uploading
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()
        firebaseStorage = FirebaseStorage.getInstance()
        val storageReference = firebaseStorage.reference
        storageReference.child("Drivers Media").child(driverDetailsId).child(imageName)
            .putFile(imageUri)
            .addOnSuccessListener { // Image uploaded successfully
                // Dismiss dialog
                progressDialog.dismiss()
                Toast
                    .makeText(
                        this,
                        "Data send to firebase" /*"Image Uploaded!!"*/,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
            .addOnFailureListener { e -> // Error, Image not uploaded
                progressDialog.dismiss()
                Toast
                    .makeText(
                        this,
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

    private fun driverValidation(): Boolean {
        if (etDriverName.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "Enter Driver Name", Toast.LENGTH_SHORT).show()
            return false
        } else if (etDriverPhoneNumbe.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "Enter Driver Mobile Number", Toast.LENGTH_SHORT).show()
            return false
        } else if (etDriverLicenseNo.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "Enter Driver License No", Toast.LENGTH_SHORT).show()
            return false
        } else if (etDriverAadhaarNo.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "Enter Driver Aadhaar No", Toast.LENGTH_SHORT).show()
            return false
        } else if (etDriverAddress.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "Enter Driver Address", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}