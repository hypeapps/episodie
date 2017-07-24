@file:Suppress("NOTHING_TO_INLINE")

package pl.hypeapp.episodie.extensions

import android.util.Log

inline fun v(tag: String, msg: String) = Log.v(tag, msg)

inline fun d(tag: String, msg: String) = Log.d(tag, msg)

inline fun i(tag: String, msg: String) = Log.i(tag, msg)

inline fun w(tag: String, msg: String) = Log.w(tag, msg)

inline fun e(tag: String, msg: String) = Log.e(tag, msg)

private val Any.tag: String
    get() = javaClass.simpleName
