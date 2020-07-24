package com.example.covid_19_support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(val messageList:ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    interface OnItemClickListenner {
        fun OnItemClick(holder : ViewHolder, view:View, data:String,position:Int)
    }
    var itemClickListenner:OnItemClickListenner?=null

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById(R.id.title) as TextView
        val body = itemView.findViewById(R.id.body) as TextView
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.message_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = messageList[position].time
        holder.body.text = messageList[position].body
    }
}