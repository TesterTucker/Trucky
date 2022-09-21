package com.frist.turkey.ui.Vehicle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.frist.turkey.R
import com.frist.turkey.base.BaseActivity
import com.frist.turkey.ui.Vehicle.fragment.BrokerVehicleFragment
import com.frist.turkey.ui.Vehicle.fragment.MarketVehicleFragment
import com.frist.turkey.ui.Vehicle.fragment.OwnVehicleFragment
import kotlinx.android.synthetic.main.activity_vehicle.*

class VehicleActivity :BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle)
        initViews()
        initControl()
    }

    override fun initViews() {
        constraintOwnVehicleBackground()
        showFragment(OwnVehicleFragment())
    }

    override fun initControl() {
        constraintOwnVehicle.setOnClickListener(this)
        constraintMarketVehicle.setOnClickListener(this)
        constraintBroker.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.constraintOwnVehicle->{
                constraintOwnVehicleBackground()

            }
            R.id.constraintMarketVehicle->{

                constraintMarketVehicleBackground()

            }
            R.id.constraintBroker->{
                constraintBrokerBackground()
            }
        }
    }

    private fun constraintBrokerBackground() {
        tvHeadingVehicle.text="Broker Vehicle"
        showFragment(BrokerVehicleFragment())

    }


    private fun constraintMarketVehicleBackground() {
        tvHeadingVehicle.text="Market Vehicle"
        constraintMarketVehicle.setBackgroundResource(R.drawable.bg_save)
        tvMarket_Vehicle.setTextColor(Color.parseColor("#FFFFFF"))
        tvMarket_Vehicle_SubHeading.setTextColor(Color.parseColor("#FFFFFF"))
        constraintOwnVehicle.setBackgroundResource(R.color.white)
        tvOwnVehicle.setTextColor(Color.parseColor("#858585"))
        tvOwnVehicleSubHeading.setTextColor(Color.parseColor("#858585"))
        constraintBroker.setBackgroundResource(R.color.white)
        tvBroker.setTextColor(Color.parseColor("#858585"))
        tvBroker_SubHeading.setTextColor(Color.parseColor("#858585"))
        showFragment(MarketVehicleFragment())
    }

    private fun constraintOwnVehicleBackground() {
        tvHeadingVehicle.text="Own Vehicle"
        constraintOwnVehicle.setBackgroundResource(R.drawable.bg_save)
        tvOwnVehicle.setTextColor(Color.parseColor("#FFFFFF"))
        tvOwnVehicleSubHeading.setTextColor(Color.parseColor("#FFFFFF"))
        constraintMarketVehicle.setBackgroundResource(R.color.white)
        tvMarket_Vehicle.setTextColor(Color.parseColor("#858585"))
        tvMarket_Vehicle_SubHeading.setTextColor(Color.parseColor("#858585"))
        showFragment(OwnVehicleFragment())
    }

    private  fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_vehicle, fragment)
            .commit()
    }
}