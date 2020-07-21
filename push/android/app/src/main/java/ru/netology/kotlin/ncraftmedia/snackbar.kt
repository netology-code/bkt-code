@file:Suppress("NOTHING_TO_INLINE")

package ru.netology.kotlin.ncraftmedia

import android.view.View
import com.google.android.material.snackbar.Snackbar

inline fun View.longSnackbar(message: CharSequence, length: Int = Snackbar.LENGTH_LONG) = Snackbar
    .make(this, message, length)
    .apply { show() }