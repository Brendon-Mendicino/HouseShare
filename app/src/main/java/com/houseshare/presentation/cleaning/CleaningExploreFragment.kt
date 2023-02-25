package com.houseshare.presentation.cleaning

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.motion.MotionUtils
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.transition.MaterialContainerTransform
import com.houseshare.R
import com.houseshare.databinding.FragmentCleaningExploreBinding
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass.
 * Use the [CleaningExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CleaningExploreFragment : Fragment() {

    private val viewModel: CleaningViewModel by activityViewModels()

    private var _binding: FragmentCleaningExploreBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = MotionUtils.resolveThemeDuration(
                requireContext(),
                com.google.android.material.R.attr.motionDurationMedium4,
                500
            ).toLong()
            interpolator = MotionUtils.resolveThemeInterpolator(
                requireContext(),
                com.google.android.material.R.attr.motionEasingEmphasizedAccelerateInterpolator,
                FastOutSlowInInterpolator()
            )
            scrimColor = Color.TRANSPARENT

            // this preserves the card corners during the transition
            val model = ShapeAppearanceModel.Builder().setAllCorners(
                CornerFamily.ROUNDED,
                resources.getDimension(R.dimen.card_cleaning_list_corner)
            ).build()
            startShapeAppearanceModel = model
            endShapeAppearanceModel = model
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCleaningExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        binding.run {
            viewModel.selectedCleaning.observe(viewLifecycleOwner) {
                val context = context ?: return@observe
                val cleaning = it ?: return@observe

                val firstDate = DateTimeFormatter
                    .ofPattern("EEE dd MMM yyyy")
                    .format(cleaning.referenceWeek.start)
                val lastDate = DateTimeFormatter
                    .ofPattern("dd MMM yyyy")
                    .format(cleaning.referenceWeek.endInclusive)

                week.text = cleaning.id.toString()

                week.text = resources.getString(R.string.week_with_number, cleaning.id)
                interval.text =
                    resources.getString(
                        R.string.date_interval,
                        "$firstDate - $lastDate"
                    )

                val (completed, color) = if (cleaning.isCompleted) {
                    Pair(
                        resources.getText(R.string.completed),
                        ContextCompat.getColor(context, R.color.desaturated_green)
                    )
                } else {
                    Pair(
                        resources.getText(R.string.incomplete),
                        ContextCompat.getColor(context, R.color.desaturated_red)
                    )
                }

                completeIndicator.text = completed
                completeIndicator.compoundDrawablesRelative.filterNotNull().forEach { drawable ->
                    drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
            }
        }
    }

}