package com.example.realtimedatabase

sealed class ResultState<out T> {
    object Loading:ResultState<Nothing>()
    data class Success<T>(val data:T):ResultState<T>()
    data class Error(val error :Throwable):ResultState<Nothing>()
}