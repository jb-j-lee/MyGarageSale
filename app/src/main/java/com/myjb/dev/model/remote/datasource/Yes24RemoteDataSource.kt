package com.myjb.dev.model.remote.datasource

import com.myjb.dev.model.remote.jsoup.PriceInquiry2Yes24
import com.myjb.dev.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.system.measureTimeMillis

private const val TAG = "Yes24RemoteDataSource"

object Yes24RemoteDataSource : DataSource {
    override suspend fun getBooks(text: String): Flow<APIResponse> = flow {
        emit(APIResponse.Loading)

        val time = measureTimeMillis {
            val result = PriceInquiry2Yes24(query = text).getPriceInfo()
            emit(APIResponse.Success(result))
        }
        Logger.e(TAG, "[getBooks] time : $time")
    }.flowOn(Dispatchers.IO)
}