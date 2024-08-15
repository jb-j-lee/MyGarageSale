package com.myjb.dev.model.remote.datasource

import com.myjb.dev.model.remote.dto.BookInfoItem
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getBooks(text: String): Flow<List<BookInfoItem>>
}