package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

class SignUp : ComponentActivity() {
    private lateinit var edtName: EditText
    private  lateinit var edtEmail: EditText
    private  lateinit var edtPassword: EditText
    private  lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSignup)

        btnSignUp.setOnClickListener() {
            if (edtName.text.toString().isEmpty()) {
                edtName.error = "Please enter name"
                edtName.requestFocus()
                return@setOnClickListener
            } else if (edtEmail.text.toString().isEmpty()) {
                edtEmail.error = "Please enter email"
                edtEmail.requestFocus()
                return@setOnClickListener
            } else if (edtPassword.text.toString().isEmpty()) {
                edtPassword.error = "Please enter password"
                edtPassword.requestFocus()
                return@setOnClickListener
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}