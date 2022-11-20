package com.example.studentportal.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.studentportal.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.emailInputField)
        etPassword = findViewById(R.id.passwordInputField)
        btnSignIn = findViewById(R.id.loginButton)
        btnRegister = findViewById(R.id.registerButton)

        firebaseAuth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val email = etEmail.text.toString()
        val pass = etPassword.text.toString()

        if(email.isEmpty()) {
            etEmail.error = "Введите Email"
        }

        if(pass.isEmpty()) {
            etPassword.error = "Введите пароль"
        }

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
/*
// Пользователь уже зарегистрирован
    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
*/
}