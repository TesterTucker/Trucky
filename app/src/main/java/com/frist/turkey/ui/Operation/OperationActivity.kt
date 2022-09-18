package com.frist.turkey.ui.Operation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.frist.turkey.R
import com.frist.turkey.base.BaseActivity
import com.frist.turkey.ui.Vehicle.VehicleActivity
import kotlinx.android.synthetic.main.activity_operation.*

class OperationActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)
        initViews()
        initControl()
    }

    override fun initViews() {

    }

    override fun initControl() {
        btnBookNew_Operation.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
       when(p0?.id){
           R.id.btnBookNew_Operation->{
               startActivity(Intent(this, VehicleActivity::class.java))

           }
       }
    }
}