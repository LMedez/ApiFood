package com.luc.apifood.ui.utils

import android.view.View
import androidx.core.view.isVisible

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.toggleVisibility() {
    if (this.isVisible) {
        this.visibility = View.INVISIBLE
    } else {
        this.visibility = View.VISIBLE
    }
}