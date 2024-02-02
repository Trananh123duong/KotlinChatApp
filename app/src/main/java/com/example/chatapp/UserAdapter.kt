package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context, val userList: ArrayList<User>):
    //Kế thừa RecyclerView.Adapter
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

        //Tạo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        //
        val view : View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
            //Trả về ViewHolder
        return UserViewHolder(view)
    }

    //Gán dữ liệu cho ViewHolder
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        //Lấy user hiện tại
        val currentUser = userList[position]
        //Gán tên của user hiện tại cho TextView
        holder.textName.text = currentUser.name
        //Bắt sự kiện khi click vào user
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", FirebaseAuth.getInstance().currentUser?.uid)
            context.startActivity(intent)
        }
    }

    //Trả về số lượng user
    override fun getItemCount(): Int {
        //Trả về số lượng user
        return userList.size
    }

    //Tạo ViewHolder
    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        //Khai báo TextView
        val textName = itemView.findViewById<TextView>(R.id.txt_name)
    }
}