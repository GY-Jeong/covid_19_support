package com.example.covid_19_support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResultAdapter(val resultID:ArrayList<String>, val resultNAME:ArrayList<String>) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.result_list,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return resultID.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.resultList.text = resultNAME[position] // 여기서 파베에 접근해서 제목을 가져오도록 수정해야됨
    }
}