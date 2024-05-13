package com.example.realtimedatabase.a

data class RealTimeModelResponse1 (
    val item: RealTimeItems1?,
    val key:String?
){
    data class RealTimeItems1 (
            val tittle:String="",
            val description:String="",
            )
}
