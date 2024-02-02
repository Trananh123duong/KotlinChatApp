package com.example.chatapp.authenActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.chatapp.MainActivity
import com.example.chatapp.R
import com.example.chatapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : ComponentActivity() {
    private lateinit var edtName: EditText
    private  lateinit var edtEmail: EditText
    private  lateinit var edtPassword: EditText
    private  lateinit var btnSignUp: Button

    private  lateinit var mAuth: FirebaseAuth
    private  lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSignup)

        btnSignUp.setOnClickListener() {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            signUp(name, email, password)
        }

//        btnSignUp.setOnClickListener() {
//            if (edtName.text.toString().isEmpty()) {
//                edtName.error = "Please enter name"
//                edtName.requestFocus()
//                return@setOnClickListener
//            } else if (edtEmail.text.toString().isEmpty()) {
//                edtEmail.error = "Please enter email"
//                edtEmail.requestFocus()
//                return@setOnClickListener
//            } else if (edtPassword.text.toString().isEmpty()) {
//                edtPassword.error = "Please enter password"
//                edtPassword.requestFocus()
//                return@setOnClickListener
//            } else {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }
//        }
    }

    private fun signUp (name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for jump to home
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Sign Up failed. Try again after some time.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("Users").child(uid).setValue(User(name, email, uid))
    }
}

