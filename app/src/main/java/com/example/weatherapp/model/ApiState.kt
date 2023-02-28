package com.example.weatherapp.model

sealed class ApiState {
    class Success(val data: Welcome) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()
}

sealed class ApiStateList {
    class Success(val data: List<Welcome>) : ApiStateList()
    class Failure(val msg: Throwable) : ApiStateList()
    object Loading : ApiStateList()
}