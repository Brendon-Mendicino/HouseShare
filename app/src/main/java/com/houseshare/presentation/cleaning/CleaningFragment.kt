package com.houseshare.presentation.cleaning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.houseshare.databinding.FragmentCleaningBinding

class CleaningFragment : Fragment() {

    companion object {
        const val TAG = "CleaningFragment"
    }

    private val viewModel: CleaningViewModel by viewModels()

    private var _binding: FragmentCleaningBinding? = null
    private val binding get() = _binding!!

    private lateinit var listAdapter: CleaningListAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        listAdapter = CleaningListAdapter().apply {
            submitList(viewModel.cleaningList.value)
        }

        binding.cleaningList.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.cleaningList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }
}