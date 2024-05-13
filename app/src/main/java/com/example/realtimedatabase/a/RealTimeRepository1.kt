package com.example.realtimedatabase.a

import kotlinx.coroutines.flow.Flow

interface RealTimeRepository1 {

    fun insert1(
        items: RealTimeModelResponse1.RealTimeItems1
    ):Flow<ResultState1<String>>

    fun getItems1():Flow<ResultState1<List<RealTimeModelResponse1>>>

    fun delete1(key:String):Flow<ResultState1<String>>

    fun update1(res: RealTimeModelResponse1):Flow<ResultState1<String>>

}