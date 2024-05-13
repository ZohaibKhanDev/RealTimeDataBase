package com.example.realtimedatabase.a

sealed class ResultState1<out T> {
    object Loading: ResultState1<Nothing>()
    data class Success<T>(val data:T): ResultState1<T>()
    data class Error(val error :Throwable): ResultState1<Nothing>()
}