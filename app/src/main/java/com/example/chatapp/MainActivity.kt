package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.adapter.UserAdapter
import com.example.chatapp.authenActivity.Login
import com.example.chatapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : ComponentActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.userRecyclerView)

        //Hiển thị danh sách user
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        //Gán adapter cho RecyclerView
        userRecyclerView.adapter = adapter

        //Lấy danh sách user từ database
        mDbRef.child("Users").addValueEventListener(object : ValueEventListener {
            //Khi có sự thay đổi dữ liệu trên database
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xóa danh sách user cũ
                userList.clear()
                //Duyệt qua từng user trong danh sách
                for (postSnapshot in snapshot.children) {
                    //Lấy thông tin của user
                    val user = postSnapshot.getValue(User::class.java)
                    //Bỏ tên của user hiện tại ra khỏi danh sách
                    if (mAuth.currentUser?.uid != user?.uid) {
                        userList.add(user!!)
                    }
                }
                //Cập nhật lại danh sách user
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    //Hiển thị menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Xử lý sự kiện khi click vào menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            mAuth.signOut()
            val intent = Intent(this, Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}