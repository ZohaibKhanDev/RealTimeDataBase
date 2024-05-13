package com.example.realtimedatabase.a

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RealTimeViewModel1(private val repo: RealTimeRepository1) : ViewModel() {
    private val _updatedRes1: MutableState<RealTimeModelResponse1> = mutableStateOf(
        RealTimeModelResponse1(
            item = RealTimeModelResponse1.RealTimeItems1(),
            ""
        )
    )
    val updatedRes1: State<RealTimeModelResponse1> = _updatedRes1
    fun setData1(items: RealTimeModelResponse1) {
        _updatedRes1.value = items
    }

    private val _res1: MutableState<Itemstate1> = mutableStateOf(Itemstate1())
    val res1: State<Itemstate1> = _res1
    fun insert1(items: RealTimeModelResponse1.RealTimeItems1) = repo.insert1(items)
    init {
        viewModelScope.launch {
            repo.getItems1().collect {
                when (it) {
                    is ResultState1.Error -> {
                        _res1.value= Itemstate1(
                            error = it.error.toString()
                        )
                    }

                    ResultState1.Loading -> {
                        _res1.value= Itemstate1(
                            Loading = true
                        )
                    }
                    is ResultState1.Success -> {
                        _res1.value = Itemstate1(
                            item = it.data
                        )
                    }
                }
            }
        }
    }
    fun update(items: RealTimeModelResponse1) = repo.update1(items)
    fun delete(key:String) = repo.delete1(key)
}

data class Itemstate1(
    val item: List<RealTimeModelResponse1> = emptyList(),
    val error: String = "",
    val Loading: Boolean = false
)