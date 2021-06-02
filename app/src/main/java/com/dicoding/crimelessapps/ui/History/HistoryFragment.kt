package com.dicoding.crimelessapps.ui.History

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.crimelessapps.databinding.FragmentHistoryBinding
import com.dicoding.crimelessapps.ui.Data.DataNotif
import com.dicoding.crimelessapps.ui.adapter.AdapterText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var historyViewModel: HistoryViewModel
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        historyViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        getData()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                rv.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = adapterTx
                }
            }
            .addOnFailureListener {
                Log.v("Failed", "to get the data!")
            }
    }
}