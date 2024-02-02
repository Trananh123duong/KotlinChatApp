package com.example.chatapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.adapter.MessageAdapter
import com.example.chatapp.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView

    //danh sách tin nhắn
    private  lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>

    private lateinit var mDbRef: DatabaseReference

    //Phòng chat của người nhận
    var receiverRoom: String? = null
    //Phòng chat của người gửi
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //Lấy tên và uid của người nhận
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        //Lấy uid của người gửi
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        mDbRef = FirebaseDatabase.getInstance().getReference()

        //Tạo phòng chat của người gửi và người nhận
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        //Hiển thị tên của người nhận
        supportActionBar?.title = name


        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sentButton)

        //Tạo danh sách tin nhắn
        messageList = ArrayList()
        //Tạo adapter
        messageAdapter = MessageAdapter(this, messageList)

        //Hiển thị danh sách tin nhắn
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        //Gán adapter cho RecyclerView
        chatRecyclerView.adapter = messageAdapter

        //Lấy danh sách tin nhắn từ Firebase
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                //Khi có thay đổi
                override fun onDataChange(snapshot: DataSnapshot) {
                    //Xóa danh sách tin nhắn
                    messageList.clear()
                    //Duyệt qua danh sách tin nhắn
                    for (postSnapshot in snapshot.children) {
                        //Lấy tin nhắn
                        val message = postSnapshot.getValue(Message::class.java)
                        //Thêm tin nhắn vào danh sách
                        messageList.add(message!!)
                    }
                    //Thông báo cho adapter biết danh sách tin nhắn đã thay đổi
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    //Do nothing
                }
            })

        //Bắt sự kiện khi click vào nút gửi
        sendButton.setOnClickListener {
            //Lấy tin nhắn
            val message = messageBox.text.toString()
            //Tạo đối tượng tin nhắn
            val messageObject = Message(message, senderUid)

            //Gửi tin nhắn
            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }
    }
}