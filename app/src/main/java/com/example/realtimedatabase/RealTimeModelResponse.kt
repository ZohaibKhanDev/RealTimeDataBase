package com.example.realtimedatabase

data class RealTimeModelResponse (
    val item:RealTimeItems?,
    val key:String?
){
    data class RealTimeItems (
            val tittle:String="",
            val description:String="",
            )
}
