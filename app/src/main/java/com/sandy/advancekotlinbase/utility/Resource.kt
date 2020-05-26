package com.sandy.advancekotlinbase.utility

data class Resource<out T>(val result: Result, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(result = Result.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(result = Result.ERROR, data = data, message = message)

        fun <T> loading(): Resource<T> =
            Resource(result = Result.LOADING, data = null, message = null)
    }
}