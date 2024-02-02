package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter (val context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        val ITEM_RECEIVE = 1
        val ITEM_SENT = 2
        //Tạo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_RECEIVE) {
            //Tạo View từ layout receive.xml
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            //Trả về ViewHolder
            return ReceivedViewHolder(view)
        }
        else {
            //Tạo View từ layout send.xml
            val view: View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            //Trả về ViewHolder
            return SentViewHolder(view)
        }
    }

    //Gán dữ liệu cho ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Lấy tin nhắn hiện tại
        val currentMessage = messageList[position]
        //Lấy tin nhắn hiện tại
        if (holder.javaClass == SentViewHolder::class.java) {
            //Ép kiểu holder về SentViewHolder
            val viewHolder = holder as SentViewHolder
            //Gán tin nhắn cho TextView
            holder.sentMessage.text = currentMessage.message
        }
        //Nếu tin nhắn là tin nhắn nhận
        else {
            //Ép kiểu holder về ReceivedViewHolder
            val viewHolder = holder as ReceivedViewHolder
            //Gán tin nhắn cho TextView
            holder.receivedMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        //Lấy tin nhắn hiện tại
        val currentMessage = messageList[position]
        //Nếu tin nhắn hiện tại là tin nhắn gửi
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            //Trả về ITEM_SENT
            return ITEM_SENT
        }
        //Nếu tin nhắn hiện tại là tin nhắn nhận
        else {
            //Trả về ITEM_RECEIVE
            return ITEM_RECEIVE
        }
    }

    //Trả về số lượng tin nhắn
    override fun getItemCount(): Int {
        return messageList.size
    }

    //Tạo ViewHolder cho tin nhắn gửi
    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_send_message)
    }

    //Tạo ViewHolder cho tin nhắn nhận
    class ReceivedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val receivedMessage = itemView.findViewById<TextView>(R.id.txt_receive_message)
    }
}