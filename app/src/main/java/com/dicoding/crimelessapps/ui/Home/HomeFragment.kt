package com.dicoding.crimelessapps.ui.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.crimelessapps.databinding.FragmentHomeBinding
import com.dicoding.crimelessapps.ui.Data.DataNotif
import com.dicoding.crimelessapps.ui.adapter.AdapterText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
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
        getData()
        return root
    }

    private fun getData() {
        db.collection("DummyData")
            .get()
            .addOnSuccessListener {
                val listContent: ArrayList<DataNotif> = ArrayList()
                listContent.clear()
                for (doc in it) {
                    listContent.add(
                        (DataNotif(
                            doc.data["Icon"].toString(),
                            doc.data["Title"].toString(),
                            doc.data["Desc"].toString(),
                            doc.data["Time"].toString()
                        ))
                    )
                }

                val adapterTx = AdapterText(listContent)
                rv_content.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = adapterTx
                }
            }
            .addOnFailureListener {
                Log.v("Failed", "to get the data!")
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}