package com.dicoding.crimelessapps.ui.Detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.crimelessapps.R
import com.dicoding.crimelessapps.databinding.FragmentActivityBinding

class ActivityFragment : Fragment() {

    private var _binding: FragmentActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}