package com.houseshare.presentation.util

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
fun Context.getColorFromAttribute(@AttrRes attrRes: Int): Int {
    return TypedValue().run {
        theme.resolveAttribute(
            attrRes,
            this,
            true
        )

        data
    }
}