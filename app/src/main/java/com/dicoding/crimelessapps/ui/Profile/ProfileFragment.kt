package com.dicoding.crimelessapps.ui.Profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.crimelessapps.databinding.FragmentProfileBinding
import com.dicoding.crimelessapps.ui.Authentication.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        // log out
        btn_logout.setOnClickListener {
            auth.signOut()
            Intent(context, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
//        getData()
    }

//    private fun getData() = CoroutineScope(Dispatchers.Main).launch {
//        val collectionRef = Firebase.firestore.collection("PrivateData")
//        val querySnapshot = collectionRef.get().await()
//        val data = querySnapshot.documents[1]
//        val name = data.get("name")
//        val address = data.get("address")
//        val phone = data.get("phone")
////        val email = data.get("email")
//        tv_name_value.text = name.toString()
//        tv_address_value.text = address.toString()
//        tv_phone_value.text = phone.toString()
////        tv_email_value.text = email.toString()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}