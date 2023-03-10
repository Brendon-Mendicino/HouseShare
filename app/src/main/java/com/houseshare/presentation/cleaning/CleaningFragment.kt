package com.houseshare.presentation.cleaning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import com.google.android.material.motion.MotionUtils
import com.google.android.material.transition.MaterialElevationScale
import com.houseshare.R
import com.houseshare.databinding.FragmentCleaningBinding

class CleaningFragment : Fragment() {

    companion object {
        const val TAG = "CleaningFragment"
    }

    private val viewModel: CleaningViewModel by activityViewModels()

    private var _binding: FragmentCleaningBinding? = null
    private val binding get() = _binding!!

    private lateinit var listAdapter: CleaningListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        exitTransition = MaterialElevationScale(false).apply {
//            duration = MotionUtils.resolveThemeDuration(
//                requireContext(),
//                com.google.android.material.R.attr.motionDurationLong2,
//                500
//            ).toLong()
//        }
//        reenterTransition = MaterialElevationScale(true).apply {
//            duration = MotionUtils.resolveThemeDuration(
//                requireContext(),
//                com.google.android.material.R.attr.motionDurationLong2,
//                500
//            ).toLong()
//
//            addListener(object: TransitionListenerAdapter() {
//                override fun onTransitionEnd(transition: Transition) {
//                    super.onTransitionEnd(transition)
//                }
//
//            })
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCleaningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Postpone enter transitions to allow shared element transitions to run.
        // https://github.com/googlesamples/android-architecture-components/issues/495
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        listAdapter = CleaningListAdapter { view, cleaning ->
            val transitionName = getString(R.string.cleaning_container_transition_name)
            val extras = FragmentNavigatorExtras(view to transitionName)
            val action =
                CleaningFragmentDirections.actionCleaningFragmentToCleaningExploreFragment()

            viewModel.setSelectedCleaning(cleaning)

            setupTransitionForSharedElementTransition()

            findNavController().navigate(action, extras)
        }
        listAdapter.submitList(viewModel.cleaningList.value)

        binding.cleaningList.apply {
            adapter = listAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        viewModel.cleaningList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }

    private fun setupTransitionForSharedElementTransition() {
        val initialExitTransition = exitTransition
        val initialReenterTransition = reenterTransition


        exitTransition = MaterialElevationScale(false).apply {
            duration = MotionUtils.resolveThemeDuration(
                requireContext(),
                com.google.android.material.R.attr.motionDurationLong2,
                500
            ).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = MotionUtils.resolveThemeDuration(
                requireContext(),
                com.google.android.material.R.attr.motionDurationLong2,
                500
            ).toLong()

            // reset initial transitions
            addListener(object: TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    super.onTransitionEnd(transition)
                    exitTransition = initialExitTransition
                    reenterTransition = initialReenterTransition
                }

            })
        }
    }

}