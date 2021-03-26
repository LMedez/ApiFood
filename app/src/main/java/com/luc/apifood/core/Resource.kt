package com.luc.apifood.core

import java.lang.Exception

sealed class Resource <out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val exception: Exception?, val message: String) : Resource<T>()
}