package com.frist.turkey.ui.home.fragment.ClientDetailFragment

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.frist.turkey.R
import com.frist.turkey.base.BaseActivity
import com.frist.turkey.model.ClientDetail
import com.frist.turkey.model.Driver
import com.frist.turkey.utils.SpinnerCallback
import com.frist.turkey.utils.showToast
import com.frist.turkey.utils.spinnerMonth
import com.frist.turkey.utils.statusBarTransparent
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_client.*
import kotlinx.android.synthetic.main.fragment_driver.*

class AddClientActivity :BaseActivity() , View.OnClickListener{
    var clientList= arrayListOf<String>()
    lateinit var databaseReference: DatabaseReference

    lateinit var firebaseStorage: FirebaseStorage
    private  var  editCLientType:String=""
    var ImageUri: Uri? = null
    private val clientModel: ClientDetail?
        get() = intent.getParcelableExtra("isClientEdit") ?: null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)
       // statusBarTransparent()
        initViews()
        initControl()
    }

    override fun initViews() {
        clientList.addAll(arrayListOf("Consignor","Consignee","Broker"))
        databaseReference = FirebaseDatabase.getInstance().reference


        clientModel?.let {
            etClientName.setText(it.clientName)
            etClientDetailTypeOfClient.setText(it.typefClient)
            etClientDetail_PhoneNumber.setText(it.ClientMobileNumber)
            etClientDetailCompanyName.setText(it.ClientCompany)
            etClientDetailDriverAddress.setText(it.ClientAddress)
            etAccountNumber.setText(it.accountNumber)
            etBankBranch.setText(it.branchName)
            etBankName.setText(it.clientNameBank)
            etIFSCCode.setText(it.ifscCode)

            editCLientType=it.typefClient.toString()
            if (editCLientType.equals("Broker")){
                constraintBank.visibility=View.VISIBLE
            }else{
                constraintBank.visibility=View.GONE
            }
        }
    }

    private fun BankDetailVisibility(value:String) {
        if (value.equals("Broker")){
            constraintBank.visibility=View.VISIBLE
        }else{
            constraintBank.visibility=View.GONE
        }

    }

    override fun initControl() {
        btn_saveClientDetail.setOnClickListener(this)
        etClientDetailTypeOfClient.setOnClickListener(this)
        tvClientDetailPhotoUpload1.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tvClientDetailPhotoUpload1->{
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, 100)
            }
            R.id.btn_saveClientDetail->{
                if (validateClientDetail()){
                    sendClientDataToFirebase(clientModel)
                    //Toast.makeText(this, "Client Validation Done", Toast.LENGTH_SHORT).show()
                    pbClient.visibility=View.VISIBLE
                }
            }
            R.id.etClientDetailTypeOfClient->{
                spinnerMonth(etClientDetailTypeOfClient, clientList, object : SpinnerCallback {
                    override fun onSelected(value: String) {
                        BankDetailVisibility(value)
                    }
                })
            }
        }
    }
    private var clientDetailsId = ""
    private fun sendClientDataToFirebase(isEditClient: ClientDetail? = null) {
        val clientName=etClientName.text.toString()
        val typeOfClient=etClientDetailTypeOfClient.text.toString()
        val ClientMobileNumber=etClientDetail_PhoneNumber.text.toString()
        val ClientCompanyName=etClientDetailCompanyName.text.toString()
        val ClientDetailAddress=etClientDetailDriverAddress.text.toString()
        val clientNameBank=etBankName.text.toString()
        val branchName=etBankBranch.text.toString()
        val accountNumber=etAccountNumber.text.toString()
        val ifscCode=etIFSCCode.text.toString()


        clientDetailsId = isEditClient?.clientDetailsId ?: databaseReference.child("Client Detail").push().key.toString()

        if (sharedPreferences.currentUser!=null){
            val clientDetail=ClientDetail(
                clientName = clientName,
                typefClient = typeOfClient,
                ClientMobileNumber = ClientMobileNumber,
                ClientCompany = ClientCompanyName,
                ClientAddress = ClientDetailAddress,
                clientDetailsId = clientDetailsId,
                timeStamp = ServerValue.TIMESTAMP,
                clientNameBank = clientNameBank,
                branchName = branchName,
                accountNumber = accountNumber,
                ifscCode = ifscCode,
            )
            databaseReference.child("Client Detail").child(clientDetailsId).setValue(clientDetail).addOnCompleteListener {
                if (it.isSuccessful){
                    pbClient.visibility=View.GONE
                     clearClientData()
                    onBackPressed()
                    ImageUri?.let { it1 ->
                        sendClientDetailImageToFirebase(
                            imageUri = it1,
                            imageName = "Client Image"
                        )
                    }

                }else{
                    pbClient.visibility=View.GONE
                    showToast("failed")
                }

            }
        }
    }

    private fun clearClientData() {
        etClientName.setText("")
        etClientDetailTypeOfClient.setText("")
        etClientDetail_PhoneNumber.setText("")
        etClientDetailCompanyName.setText("")
        etClientDetailDriverAddress.setText("")
        etBankName.setText("")
        etBankBranch.setText("")
        etAccountNumber.setText("")
        etIFSCCode.setText("")
    }

    private fun sendClientDetailImageToFirebase(imageUri: Uri, imageName: String) {
        // Code for showing progressDialog while uploading
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()
        firebaseStorage = FirebaseStorage.getInstance()
        val storageReference = firebaseStorage.reference
        storageReference.child("Client Media").child(clientDetailsId).child(imageName)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {

            ImageUri=data?.data!!
            IvClientDetailUploadPic.setImageURI(ImageUri)
        }
    }

    fun validateClientDetail():Boolean{
        if (etClientName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show()
            return false
        }else  if (etClientDetail_PhoneNumber.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Client Mobile Number", Toast.LENGTH_SHORT).show()
            return false
        }else  if (etClientDetailTypeOfClient.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Type of Client", Toast.LENGTH_SHORT).show()
            return false
        }else  if (etClientDetailCompanyName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Company Name", Toast.LENGTH_SHORT).show()
            return false
        }else  if (etClientDetailDriverAddress.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun validateBank():Boolean{
        if (etBankName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Bank Name", Toast.LENGTH_SHORT).show()
            return false
        } else if (etAccountNumber.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Account Number", Toast.LENGTH_SHORT).show()
            return false
        }else if (etBankBranch.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Bank Branch", Toast.LENGTH_SHORT).show()
            return false
        }else if (etIFSCCode.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter IFSC Code", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}