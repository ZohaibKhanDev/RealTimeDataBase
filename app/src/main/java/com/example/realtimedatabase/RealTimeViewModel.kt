package com.example.realtimedatabase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RealTimeViewModel(private val repo: RealTimeRepository) : ViewModel() {
    private val _updatedRes: MutableState<RealTimeModelResponse> = mutableStateOf(
        RealTimeModelResponse(
            item = RealTimeModelResponse.RealTimeItems(),
            ""
        )
    )
    val updatedRes: State<RealTimeModelResponse> = _updatedRes
    fun setData(items: RealTimeModelResponse) {
        _updatedRes.value = items
    }

    private val _res: MutableState<Itemstate> = mutableStateOf(Itemstate())
    val res: State<Itemstate> = _res
    fun insert(items: RealTimeModelResponse.RealTimeItems) = repo.insert(items)
    init {
        viewModelScope.launch {
            repo.getItems().collect {
                when (it) {
                    is ResultState.Error -> {
                        _res.value=Itemstate(
                            error = it.error.toString()
                        )
                    }

                    ResultState.Loading -> {
                        _res.value=Itemstate(
                            Loading = true
                        )
                    }
                    is ResultState.Success -> {
                        _res.value = Itemstate(
                            item = it.data
                        )
                    }
                }
            }
        }
    }
    fun update(items: RealTimeModelResponse) = repo.update(items)
    fun delete(key:String) = repo.delete(key)
}

data class Itemstate(
    val item: List<RealTimeModelResponse> = emptyList(),
    val error: String = "",
    val Loading: Boolean = false
)