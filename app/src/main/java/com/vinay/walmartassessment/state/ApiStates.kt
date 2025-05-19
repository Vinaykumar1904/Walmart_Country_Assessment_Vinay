package com.vinay.walmartassessment.state

sealed class ApiStates<out T> {
    object Loading: ApiStates<Nothing>()
    data class Success<out T>(val data:T) : ApiStates<T>()
    data class Failure(val msg:String) : ApiStates<Nothing>()
}