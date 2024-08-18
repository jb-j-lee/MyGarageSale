package com.myjb.dev.model.remote.datasource

sealed class APIResponse {
    data class Success<out T>(val data: T) : APIResponse()
    data class Error(val errorCode: Int, val message: String) : APIResponse()
    object Loading : APIResponse()
}