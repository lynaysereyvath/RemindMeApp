package com.lynaysereyvath.remindme.domain.utils

sealed class Resource<T>(var data: T? = null, var message: String? = null, var exception: Exception? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, exception: Exception? = null, data: T? = null): Resource<T>(data, message, exception)
}