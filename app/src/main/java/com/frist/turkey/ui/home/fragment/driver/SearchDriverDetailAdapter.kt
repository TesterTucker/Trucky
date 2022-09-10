package com.frist.turkey.ui.home.fragment.driver

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frist.turkey.R
import com.frist.turkey.model.Driver
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.item_driver_detail.view.*

class SearchDriverDetailAdapter(var context: Context, var driverList: ArrayList<Driver>,
                        var editDriver:EditDriver) :
    RecyclerView.Adapter<SearchDriverDetailAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_driver_detail, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            Name.text=  driverList.get(position).driverName
            Phone.text=  driverList.get(position).driverMobileNumber

            editDriverDetail.setOnClickListener {
                editDriver.onClickEditDriver(driverList.get(position))
            }

        }
    }

    override fun getItemCount(): Int {
        return driverList.size
    }


    interface EditDriver {
        fun onClickEditDriver(driverModel:Driver)

    }
}