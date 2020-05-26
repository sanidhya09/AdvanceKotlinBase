package com.sandy.advancekotlinbase.utility

sealed class Result {
    object SUCCESS : Result()
    object LOADING : Result()
    object ERROR : Result()
}