package ru.sicampus.bootcamp2025.util

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import ru.sicampus.bootcamp2025.R

fun setActiveMenuItem(activeButton: ImageButton, buttons: Set<ImageButton>, context: Context) {
    activeButton.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.violet))

    buttons.filter { it != activeButton }.forEach {
        it.imageTintList = null
    }
}