package com.engineering.baseproj.utility

data class APIState<out T>(val result: Result, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): APIState<T> =
            APIState(result = Result.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): APIState<T> =
            APIState(result = Result.ERROR, data = data, message = message)

        fun <T> loading(): APIState<T> =
            APIState(result = Result.LOADING, data = null, message = null)
    }
}