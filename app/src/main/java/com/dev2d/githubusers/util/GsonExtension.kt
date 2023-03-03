package com.dev2d.githubusers.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val gson = Gson()

inline fun <reified T> String.toDataClass(): T =
    gson.fromJson(this, object : TypeToken<T>() {}.type)

/**
 * convert any object(or list of objects) to json string
 * can only be used on non null types
 * for ex:
 * val jsonString = users.toJsonString()
 */
fun Any.toJsonString(): String = gson.toJson(this)