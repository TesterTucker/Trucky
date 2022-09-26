package com.frist.turkey.ui.Operation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frist.turkey.R
import com.frist.turkey.model.ClientDetail
import com.frist.turkey.model.CommonVehicle
import com.frist.turkey.model.OwnerVehicle
import kotlinx.android.synthetic.main.item_client_detail.view.*
import kotlinx.android.synthetic.main.item_opertaion_detail.view.*


class OperationAdapter(var vehicleList: ArrayList<CommonVehicle>, val commonListener: CommonListener) :
    RecyclerView.Adapter<OperationAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_opertaion_detail, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            date.text=vehicleList[position].date
            TruckNumber.text=  vehicleList.get(position).TruckNumber
            ClientName.text=  vehicleList.get(position).ConsignorName
            editDetail.setOnClickListener {
                commonListener.editData(vehicleList.get(position))
            }
            CreateBuiltyDetail.setOnClickListener {
                commonListener.createbuiltyData(vehicleList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    interface CommonListener{
        fun editData(item:CommonVehicle)
        fun createbuiltyData(item:CommonVehicle)
    }
}