package com.myjb.dev.model.remote.datasource

import com.myjb.dev.model.data.Company
import com.myjb.dev.model.remote.dto.BookInfoItem
import com.myjb.dev.model.remote.jsoup.PriceInquiry2Aladin
import com.myjb.dev.model.remote.jsoup.PriceInquiry2Yes24
import com.myjb.dev.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.system.measureTimeMillis

const val TAG = "RemoteDataSource"

class RemoteDataSource(private val company: Company) : DataSource {
    override suspend fun getBooks(text: String): Flow<List<BookInfoItem>> = flow {
        val time = measureTimeMillis {
            val result = when (company) {
                Company.ALADIN -> {
                    PriceInquiry2Aladin(query = text).getPriceInfo()
                }

                Company.YES24 -> {
                    PriceInquiry2Yes24(query = text).getPriceInfo()
                }

                else -> {
                    TODO("Crash")
                }
            }
            emit(result)
        }
        Logger.e(TAG, "[getBooks] time : $time")
    }.flowOn(Dispatchers.IO)
}