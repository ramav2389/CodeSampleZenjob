package com.zenjob.android.browsr.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Generic View Extension for SnackBar
 */

fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}
