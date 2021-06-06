package com.dicoding.crimelessapps.ui.Authentication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.crimelessapps.MainActivity
import com.dicoding.crimelessapps.R
import com.dicoding.crimelessapps.ui.Register.PrivateDataActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        btnRegisterNow.setOnClickListener {
            val email = etEmailRegister.text.toString().trim()
            val pass = etPasswordRegister.text.toString().trim()

            if (email.isEmpty()) {
                etEmailRegister.error = "Email can't empty!"
                etEmailRegister.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmailRegister.error = "Email is not valid!"
                etEmailRegister.requestFocus()
                return@setOnClickListener
            }

            if (pass.isEmpty() || pass.length < 6) {
                etPasswordRegister.error = "Password must be more than 6 char!"
                etPasswordRegister.requestFocus()
                return@setOnClickListener
            }
            registerUser(email, pass)
        }

        btnBackToLogin.setOnClickListener {
            Intent(this@RegisterActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun registerUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { it ->
            if (it.isSuccessful) {
                Toast.makeText(this@RegisterActivity, "Successfully Save Data", Toast.LENGTH_SHORT)
                    .show()
                Intent(this@RegisterActivity, PrivateDataActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            } else {
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Intent(this@RegisterActivity, MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}