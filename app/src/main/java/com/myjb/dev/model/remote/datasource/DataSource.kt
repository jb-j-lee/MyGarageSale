package com.myjb.dev.model.remote.datasource

import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getBooks(text: String): Flow<APIResponse>
}