package com.example.covid_19_support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResultAdapter(val resultList:ArrayList<Int>) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    //파베에서 꺼낸 ID로 리스트를 만들어 가져오면, 여기서 Int로 받아 처리합니다. 후에 파베에 다시 접근해야합니다.
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val resultList = itemView.findViewById(R.id.resultList) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.result_list,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list:Int = resultList[position]

        holder.resultList.text = list.toString() // 여기서 파베에 접근해서 제목을 가져오도록 수정해야됨
    }
}