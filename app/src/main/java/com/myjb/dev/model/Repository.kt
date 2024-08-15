package com.myjb.dev.model

import com.myjb.dev.model.remote.datasource.DataSource
import com.myjb.dev.model.remote.dto.BookInfoItem
import kotlinx.coroutines.flow.Flow

class Repository(private val dataSource: DataSource) {

    suspend fun getBooks(text: String): Flow<List<BookInfoItem>> {
        return dataSource.getBooks(text = text)
    }
}