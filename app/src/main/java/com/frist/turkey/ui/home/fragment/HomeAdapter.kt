package com.frist.turkey.ui.home.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frist.turkey.R
import kotlinx.android.synthetic.main.item_home_fragment.view.*

class HomeAdapter(var context: Context,var homeList:ArrayList<homeModel>):RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.item_home_fragment,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.itemView.apply {
           tvOperationName.text=homeList[position].name
           Glide.with(this).load(homeList[position].image).into(ivOperation)
       }
    }

    override fun getItemCount(): Int {
      return homeList.size
    }
}

data class homeModel(var name:String,var image:Int)