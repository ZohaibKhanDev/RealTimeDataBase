package com.example.realtimedatabase

import kotlinx.coroutines.flow.Flow

interface RealTimeRepository {

    fun insert(
        items: RealTimeModelResponse.RealTimeItems
    ):Flow<ResultState<String>>

    fun getItems():Flow<ResultState<List<RealTimeModelResponse>>>

    fun delete(key:String):Flow<ResultState<String>>

    fun update(res:RealTimeModelResponse):Flow<ResultState<String>>

}