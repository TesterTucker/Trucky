package com.frist.turkey.ui.home.fragment.ClientDetailFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frist.turkey.R
import com.frist.turkey.model.ClientDetail
import com.frist.turkey.model.Driver
import kotlinx.android.synthetic.main.item_client_detail.view.*
import kotlinx.android.synthetic.main.item_driver_detail.view.*
import kotlinx.android.synthetic.main.item_driver_detail.view.editDriverDetail


class ClientDetailAdapter(var context: Context, var clientList: ArrayList<ClientDetail>,
                                var editClient:EditClient) :
    RecyclerView.Adapter<ClientDetailAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_client_detail, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            CLient_Name.text=  clientList.get(position).clientName
            tv_Designation.text=  clientList.get(position).typefClient

            editClientDetail.setOnClickListener {
                editClient.onClickEditDriver(clientList.get(position))
            }

        }
    }

    override fun getItemCount(): Int {
        return clientList.size
    }


    interface EditClient {
        fun onClickEditDriver(clientModel: ClientDetail)
    }
}