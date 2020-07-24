package com.example.covid_19_support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(val resultID:ArrayList<String>, val resultNAME:ArrayList<String>) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    interface OnItemClickListenner {
        fun OnItemClick(holder : ViewHolder, view:View, data:String,position:Int)
    }
    var itemClickListenner:OnItemClickListenner?=null

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val resultList = itemView.findViewById(R.id.resultList) as TextView

        init {
            itemView.setOnClickListener {
                itemClickListenner?.OnItemClick(this,it,resultNAME[adapterPosition],adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return resultID.size
    }



    override fun onBindViewHolder(holder: ResultAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultAdapter.ViewHolder {
        TODO("Not yet implemented")
    }
}