package com.dicoding.crimelessapps.ui.Register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.crimelessapps.R
import com.dicoding.crimelessapps.ui.Authentication.ConfirmationActivity
import com.dicoding.crimelessapps.ui.Authentication.LoginActivity
import com.dicoding.crimelessapps.ui.Data.PrivatePhone
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_private_phone.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class PrivatePhoneActivity : AppCompatActivity() {

    private val collectionRef = Firebase.firestore.collection("PrivatePhone")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_phone)
        btnFinish.setOnClickListener {
            val primOne = etPhone_priority_1.text.toString()
            val primTwo = etPhone_priority_2.text.toString()

            if (primOne.isEmpty()) {
                etPhone_priority_1.error = "Phone number can't empty!"
                etPhone_priority_1.requestFocus()
                return@setOnClickListener
            }

            if (primTwo.isEmpty()) {
                etPhone_priority_2.error = "Phone number can't empty!"
                etPhone_priority_2.requestFocus()
                return@setOnClickListener
            }

            val phonePrim = PrivatePhone(primOne, primTwo)
            savePrivatePhone(phonePrim)
        }
    }

    private fun savePrivatePhone(phonePrim: PrivatePhone) =
        CoroutineScope((Dispatchers.IO)).launch {
            try {
                collectionRef.add(phonePrim).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@PrivatePhoneActivity,
                        "Successfully Save Data",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    delay(2000)
                    Intent(this@PrivatePhoneActivity, ConfirmationActivity::class.java).also {
                        startActivity(it)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PrivatePhoneActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
}