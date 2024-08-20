package com.myjb.dev.model.remote.datasource

import com.myjb.dev.model.remote.dto.BookInfoItem

sealed class APIResponse {
    data class Success(val data: List<BookInfoItem>) : APIResponse()
    data class Error(val errorCode: Int, val message: String) : APIResponse()
    data object Loading : APIResponse()
}