package ru.netology.ncraftmedia.authorization

import android.app.ProgressDialog
import android.content.Context

@Deprecated(
    "Осторожно, блокирует весь экран",
    ReplaceWith("Используйте крутилки в ваших лэйаутах вместо вот этого всего")
)
inline fun Context.indeterminateProgressDialog(
    message: Int? = null,
    title: Int? = null,
    cancelable: Boolean = false,
    init: (ProgressDialog.() -> Unit) = {}
) = progressDialog(
    indeterminate = true,
    message = message?.let { getString(it) },
    title = title?.let { getString(it) },
    cancelable = cancelable,
    init = init
)

@Deprecated(
    "Осторожно, блокирует весь экран",
    ReplaceWith("Используйте крутилки в ваших лэйаутах вместо вот этого всего")
)
inline fun Context.progressDialog(
    indeterminate: Boolean,
    message: CharSequence? = null,
    title: CharSequence? = null,
    cancelable: Boolean = false,
    init: (ProgressDialog.() -> Unit) = {}
) = ProgressDialog(this).apply {
    isIndeterminate = indeterminate
    if (!indeterminate) setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
    if (message != null) setMessage(message)
    if (title != null) setTitle(title)
    setCancelable(cancelable)
    init()
    show()
}