package com.frist.turkey.ui.home.fragment.truckDetail

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
import com.frist.turkey.model.TruckDetail
import com.frist.turkey.ui.home.HomeActivity
import com.frist.turkey.utils.DatePickerHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_search_detail.*
import kotlinx.android.synthetic.main.fragment_driver.*
import kotlinx.android.synthetic.main.fragment_truck_detail.*
import java.util.*

class SearchDetailActivity : BaseActivity(), View.OnClickListener {
    lateinit var datePicker: DatePickerHelper
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    private var currentUser:String?=null
    var type: Int = -1
    lateinit var firebaseStorage: FirebaseStorage
    var ImageUriInsurence: Uri? = null
    var ImageUriFitness_document: Uri? = null
    var ImageUriTax: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_detail)
        initViews()
        initControl()
    }
    override fun initViews() {
        databaseReference= FirebaseDatabase.getInstance().reference
        auth=FirebaseAuth.getInstance()
        currentUser=auth.currentUser.toString()

        datePicker = DatePickerHelper(this, false)
    }

    override fun initControl() {
        btn_saveTruckDetail.setOnClickListener(this)
        cardTruckPurchaseDate.setOnClickListener(this)
        etPurchaseDate.setOnClickListener(this)
        etTruckPurchaseDate.setOnClickListener(this)
        etInsuranceDate.setOnClickListener(this)
        etFitnessDate.setOnClickListener(this)
        etFitness_Renewal_Date.setOnClickListener(this)
        etTaxDate.setOnClickListener(this)
        etTaxRenewalDate.setOnClickListener(this)
        etPermitDate.setOnClickListener(this)
        etPermit_Renewal_Date.setOnClickListener(this)
        etRenewalInsurenceDate.setOnClickListener(this)
        uplaodInsurence.setOnClickListener(this)
        card_uplaodFitness_document.setOnClickListener(this)
        card_uplaodTax_document.setOnClickListener(this)



    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.uplaodInsurence->{
                type = 1
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, 100)
            }
            R.id.card_uplaodFitness_document->{
                type = 2
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, 100)
            }
            R.id.card_uplaodTax_document->{
                type = 3
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, 100)
            }

            R.id.btn_saveTruckDetail->{
                if (validateTruckDetail()){
                    sendTruckDetailDataToFirebbase()
                    Toast.makeText(this, "Validation Done", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.etPurchaseDate->{
                showDatePickerDialog(0)
            }
            R.id.etTruckPurchaseDate -> {
                showDatePickerDialog(1)
            }
            R.id.etInsuranceDate -> {
                showDatePickerDialog(2)
            }
            R.id.etFitnessDate -> {
                showDatePickerDialog(3)
            }
            R.id.etFitness_Renewal_Date -> {
                showDatePickerDialog(4)
            }
            R.id.etTaxDate -> {
                showDatePickerDialog(5)
            }
            R.id.etTaxRenewalDate -> {
                showDatePickerDialog(6)
            }
            R.id.etPermitDate -> {
                showDatePickerDialog(7)
            }
            R.id.etPermit_Renewal_Date -> {
                showDatePickerDialog(8)
            }
            R.id.etRenewalInsurenceDate -> {
                showDatePickerDialog(9)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            when (type) {
                1 -> {
                    ImageUriInsurence = data?.data
                    ivUpload_Insurence.setImageURI(ImageUriInsurence)
                }
                2 -> {
                    ImageUriFitness_document = data?.data
                    ivuplaodFitness_document.setImageURI(ImageUriFitness_document)
                }
                3 -> {
                    ImageUriTax = data?.data
                    ivuplaodTax_document.setImageURI(ImageUriTax)
                }
            }
        }
    }

    private var truckDetailsId=""
    private fun sendTruckDetailDataToFirebbase() {
        //Toast.makeText(context, "Truck Detail data add Successfully", Toast.LENGTH_SHORT).show()
        var DealerName=etDealerName.text.toString().trim()
        var TruckBrand=ettvTruckBrand.text.toString().trim()
        var TruckRate=etTruckRate.text.toString().trim()
        var DealerPurchaseDate=etPurchaseDate.text.toString().trim()
        var TruckModel=ettvModel.text.toString().trim()
        var TruckNumber=etTruckNumber.text.toString().trim()
        var TruckPurchaseDate=etTruckPurchaseDate.text.toString().trim()
        var ChaiseNumber=etChaiseNumber.text.toString().trim()
        var NoOfTyres=ettvNoOfTyres.text.toString().trim()
        var InsurenceCompanyName=etCompanyName.text.toString().trim()
        var InsurenceDate=etInsuranceDate.text.toString().trim()
        var RenewalInsurenceDate=etRenewalInsurenceDate.text.toString().trim()
        var InsuranceNo=etInsuranceNo.text.toString().trim()
        var InsuranceRenewalDay=etRenewalAfterDay.text.toString().trim()
        var InsuranceRenewalMonth=etRenewalAfterMonth.text.toString().trim()
        var FitnessCompanyName=etFitnessCompanyName.text.toString().trim()
        var FitnessDate=etFitnessDate.text.toString().trim()
        var Fitness_Renewal_Date=etFitness_Renewal_Date.text.toString().trim()
        var FitnessNo=etFitnessNo.text.toString().trim()
        var Fitness_RenewalAfterDay=etFitness_RenewalAfterDay.text.toString().trim()
        var Fitness_RenewalAfterMonth=etFitness_RenewalAfterMonth.text.toString().trim()
        var TaxCompanyName=etTaxCompanyName.text.toString().trim()
        var TaxDate=etTaxDate.text.toString().trim()
        var TaxRenewalDate=etTaxRenewalDate.text.toString().trim()
        var TaxNo=etTaxNo.text.toString().trim()
        var Tax_RenewalDay=etTax_RenewalAfterDay.text.toString().trim()
        var Tax_RenewalMonth=etTax_RenewalAfterMonth.text.toString().trim()
        var permitCompanyName=etpermitCompanyName.text.toString().trim()
        var PermitDate=etPermitDate.text.toString().trim()
        var Permit_Renewal_Date=etPermit_Renewal_Date.text.toString().trim()
        var PermitNo=etPermitNo.text.toString().trim()
        var PermitRenewalDay=etPermitRenewalAfterDay.text.toString().trim()
        var Permit_RenewalMonth=etPermit_RenewalAfterMonth.text.toString().trim()

        truckDetailsId = databaseReference.child("TruckDetail").push().key.toString()

        var truckDetail= TruckDetail(
            driverName = DealerName,
            truckBrand = TruckBrand,
            truckRate = TruckRate,
            DelearPurchaseDate = DealerPurchaseDate,
            truckNumber = TruckNumber,
            truckPurchaseDate = TruckPurchaseDate,
            truckChaiseNumber = ChaiseNumber,
            truckNoOfTyre = NoOfTyres,
            InsurenceCompanyName = InsurenceCompanyName,
            InsurenceDate = InsurenceDate,
            InsurenceRenewalDate = RenewalInsurenceDate,
            InsurenceRenewalDay = InsuranceRenewalDay,
            InsurenceRenewalMonth = InsuranceRenewalMonth,
            InsurenceNo = InsuranceNo,
            FitnessCompanyName = FitnessCompanyName,
            FitnessDate = FitnessDate,
            FitnessRenewalDate = Fitness_Renewal_Date,
            FitnessNumber = FitnessNo,
            FitnessRenewalDays = Fitness_RenewalAfterDay,
            FitnessRenewalMonth = Fitness_RenewalAfterMonth,
            TaxCompanyName = TaxCompanyName,
            TaxDate = TaxDate,
            TaxRenewalDate = TaxRenewalDate,
            TaxNumber = TaxNo,
            TaxDay = Tax_RenewalDay,
            TaxMonth = Tax_RenewalMonth,
            PermitCompanyname = permitCompanyName,
            PermitDate = PermitDate,
            PermitRenwalDate = Permit_Renewal_Date,
            PermitNumber = PermitNo,
            PermitRenwalDay = PermitRenewalDay,
            PermitRenwalMonth = Permit_RenewalMonth
        )
        if (currentUser!=null){
            databaseReference.child("Truck Detail").push().setValue(truckDetail).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Truck Detail Set on Database", Toast.LENGTH_SHORT).show()

                    ImageUriInsurence?.let { it1 -> sendTruckImageToFirebase(imageUri = it1, imageName = "Insurence") }
                    ImageUriFitness_document?.let { it1 -> sendTruckImageToFirebase(imageUri = it1, imageName = "Fitness_document") }
                    ImageUriTax?.let { it1 -> sendTruckImageToFirebase(imageUri = it1, imageName = "Tax") }



                }else{

                    Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()

                }
            }

        }


    }

    private fun sendTruckImageToFirebase(imageUri: Uri,imageName:String) {
        // Code for showing progressDialog while uploading
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()
        firebaseStorage = FirebaseStorage.getInstance()
        val storageReference = firebaseStorage.reference
        storageReference.child("Truck Media").child(truckDetailsId).child(imageName).putFile(imageUri)
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
    private fun showDatePickerDialog(type:Int) {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                when(type) {
                    0 -> {
                        etPurchaseDate.text = "${dayStr}-${monthStr}-${year}"
                    }
                    1 -> {
                        etTruckPurchaseDate.text = "${dayStr}-${monthStr}-${year}"
                    }
                    2 -> {
                        etInsuranceDate.text = "${dayStr}-${monthStr}-${year}"
                    }
                    3 -> {
                        etFitnessDate.text = "${dayStr}-${monthStr}-${year}"
                    }
                    4 -> {
                        etFitness_Renewal_Date.text = "${dayStr}-${monthStr}-${year}"
                    }
                    5 -> {
                        etTaxDate.text = "${dayStr}-${monthStr}-${year}"
                    }

                    6 -> {
                        etTaxRenewalDate.text = "${dayStr}-${monthStr}-${year}"
                    }
                    7 -> {
                        etPermitDate.text = "${dayStr}-${monthStr}-${year}"
                    }
                    8 -> {
                        etPermit_Renewal_Date.text = "${dayStr}-${monthStr}-${year}"
                    }
                    9 -> {
                        etRenewalInsurenceDate.text = "${dayStr}-${monthStr}-${year}"
                    }

                }
            }
        })
    }

    fun validateTruckDetail():Boolean{
        if (etDealerName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Dealear Name", Toast.LENGTH_SHORT).show()
            return false
        }else if (ettvTruckBrand.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Truck Brand", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTruckRate.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Truck Rate", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPurchaseDate.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Purchase Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (ettvModel.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Model", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTruckNumber.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Truck Number", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTruckPurchaseDate.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Truck Purchased Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (etChaiseNumber.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Truck Chaise Number ", Toast.LENGTH_SHORT).show()
            return false
        }else if (ettvNoOfTyres.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Truck Number Of Tyres ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etCompanyName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Insurenece Company Name ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etInsuranceDate.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Insurenece Date ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etRenewalInsurenceDate.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Insurenece Renewal Date ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etInsuranceNo.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Insurenece No  ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etRenewalAfterDay.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Insurenece Renewal Day  ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etRenewalAfterMonth.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Insurenece Renewal Month  ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitnessCompanyName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Fitness Company Name  ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitnessDate.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Fitness Date   ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitness_Renewal_Date.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Fitness Renewal Date   ", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitnessNo.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Fitness Number", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitness_RenewalAfterDay.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Fitness Renewal Day", Toast.LENGTH_SHORT).show()
            return false
        }else if (etFitness_RenewalAfterMonth.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter  Fitness Renewal Month", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTaxCompanyName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Tax Company Name", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTaxDate.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Tax Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTaxRenewalDate.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Tax Renewal Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTaxNo.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Tax Number", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTax_RenewalAfterDay.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Tax Renewal Day", Toast.LENGTH_SHORT).show()
            return false
        }else if (etTax_RenewalAfterMonth.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Tax Renewal Month", Toast.LENGTH_SHORT).show()
            return false
        }else if (etpermitCompanyName.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Permit Company Name", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPermitDate.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Permit Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPermit_Renewal_Date.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Permit Renewal Date", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPermitNo.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Permit Number", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPermitRenewalAfterDay.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Permit Renewal Day", Toast.LENGTH_SHORT).show()
            return false
        }else if (etPermit_RenewalAfterMonth.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "Enter Permit Renewal Month", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}