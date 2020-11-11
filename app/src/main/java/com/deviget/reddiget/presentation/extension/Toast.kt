package com.deviget.reddiget.presentation.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.toast(
    text: CharSequence,
    duration: Int = Toast.LENGTH_SHORT,
    block: Toast.() -> Unit = {}
) = Toast.makeText(this, text, duration).apply(block).also { it.show() }

fun Context.toast(
    @StringRes resId: Int,
    duration: Int = Toast.LENGTH_SHORT,
    block: Toast.() -> Unit = {}
) = Toast.makeText(this, resId, duration).apply(block).also { it.show() }

fun Fragment.toast(
    text: CharSequence,
    duration: Int = Toast.LENGTH_SHORT,
    block: Toast.() -> Unit = {}
) = context?.toast(text, duration, block)

fun Fragment.toast(
    @StringRes resId: Int,
    duration: Int = Toast.LENGTH_SHORT,
    block: Toast.() -> Unit = {}
) = context?.toast(resId, duration, block)