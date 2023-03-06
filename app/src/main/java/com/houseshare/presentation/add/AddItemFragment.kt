package com.houseshare.presentation.add

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.motion.MotionUtils
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.transition.MaterialContainerTransform
import com.houseshare.R
import com.houseshare.databinding.FragmentAddItemBinding

class AddItemFragment: Fragment() {


    private var _binding: FragmentAddItemBinding? = null
    private val binding: FragmentAddItemBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            startViewId = R.id.fab

            duration = MotionUtils.resolveThemeDuration(
                requireContext(),
                com.google.android.material.R.attr.motionDurationLong2,
                500
            ).toLong()
            interpolator = MotionUtils.resolveThemeInterpolator(
                requireContext(),
                com.google.android.material.R.attr.motionEasingEmphasizedDecelerateInterpolator,
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
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}
