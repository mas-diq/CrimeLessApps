package com.dicoding.crimelessapps.ui.Register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.crimelessapps.R
import com.dicoding.crimelessapps.ui.Data.PrivateData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_private_data.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class PrivateDataActivity : AppCompatActivity() {

    private val collectionRef = Firebase.firestore.collection("PrivateData")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_data)

        btnContinue.setOnClickListener {
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val address = etAddress.text.toString()
            val privateData = PrivateData(name, phone, address)

            if (name.isEmpty()){
                etName.error = "Name can't empty!"
                etName.requestFocus()
                return@setOnClickListener
            }

            if (phone.isEmpty()){
                etPhone.error = "Phone can't empty!"
                etPhone.requestFocus()
                return@setOnClickListener
            }

            if (address.isEmpty()){
                etAddress.error = "Address can't empty!"
                etAddress.requestFocus()
                return@setOnClickListener
            }
            savePrivateData(privateData)
        }
    }

    private fun savePrivateData(privateData: PrivateData) =
        CoroutineScope((Dispatchers.IO)).launch {
            try {
                collectionRef.add(privateData).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@PrivateDataActivity,
                        "Successfully Save Data",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    delay(2000)
                    Intent(this@PrivateDataActivity, PrivatePhoneActivity::class.java).also {
                        startActivity(it)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PrivateDataActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
}