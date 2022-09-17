package com.frist.turkey.ui.home.fragment.truckDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frist.turkey.R
import com.frist.turkey.model.Driver
import com.frist.turkey.model.TruckDetail
import kotlinx.android.synthetic.main.item_driver_detail.view.*


class TruckDetailAdapter(var context: Context, var truckDetailList: ArrayList<TruckDetail>,
                            var editTruckDetail:EditTruckDetail) :
    RecyclerView.Adapter<TruckDetailAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_driver_detail, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            Name.text=  truckDetailList.get(position).driverName
            Phone.text=  truckDetailList.get(position).truckBrand
            editDriverDetail.setOnClickListener {
                editTruckDetail.onClickEditTruck(truckDetailList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return truckDetailList.size
    }

    interface EditTruckDetail {
        fun onClickEditTruck(TruckModel:TruckDetail)
    }


}