package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.ui.theme.ChatAppTheme
import com.google.firebase.auth.FirebaseAuth

class Login : ComponentActivity() {
    private  lateinit var edtEmail: EditText
    private  lateinit var edtPassword: EditText
    private  lateinit var btnLogin: Button
    private  lateinit var btnSignUp: Button

    private  lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignup)

        btnSignUp.setOnClickListener() {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener() {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password)
        }

//        btnLogin.setOnClickListener() {
//            if (edtEmail.text.toString().isEmpty()) {
//                edtEmail.error = "Please enter email"
//                edtEmail.requestFocus()
//                return@setOnClickListener
//            } else if (edtPassword.text.toString().isEmpty()) {
//                edtPassword.error = "Please enter password"
//                edtPassword.requestFocus()
//                return@setOnClickListener
//            } else {
//                if (edtEmail.text.toString() == "duong@gmail.com" && edtPassword.text.toString() == "admin") {
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                } else {
//                    edtEmail.error = "Email or password is incorrect"
//                    edtEmail.requestFocus()
//                    return@setOnClickListener
//                }
//            }
//        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for logging in user
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Login failed. Try again after some time.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

