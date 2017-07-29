@file:Suppress("NOTHING_TO_INLINE")

package pl.hypeapp.episodie.extensions

import android.util.Log

fun Any.v(msg: String) = Log.v(tag, msg)

fun Any.d(msg: String) = Log.d(tag, msg)

fun Any.i(msg: String) = Log.i(tag, msg)

fun Any.w(msg: String) = Log.w(tag, msg)

fun Any.e(msg: String) = Log.e(tag, msg)

private val Any.tag: String
    get() = javaClass.simpleName
