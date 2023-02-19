package com.houseshare.presentation.cleaning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.houseshare.databinding.FragmentCleaningBinding

class CleaningFragment : Fragment() {

    companion object {
        fun newInstance() = CleaningFragment()
    }

    private val viewModel: CleaningViewModel by viewModels()

    private var _binding: FragmentCleaningBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCleaningBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}