package com.dicoding.crimelessapps.ui.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.crimelessapps.databinding.FragmentHomeBinding
import com.dicoding.crimelessapps.ui.adapter.Adapter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    val imageRef = Firebase.storage.reference
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        listFiles()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listFiles() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val images = imageRef.child("Icons/").listAll().await()
            val imageUrls = mutableListOf<String>()
            for (img in images.items) {
                val url = img.downloadUrl.await()
                imageUrls.add(url.toString())
            }
            withContext(Dispatchers.Main) {
                val imageAdapter = Adapter(imageUrls)
                rv_content.apply {
                    adapter = imageAdapter
                    layoutManager = LinearLayoutManager(activity)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}