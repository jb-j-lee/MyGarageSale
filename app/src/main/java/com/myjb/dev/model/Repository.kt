package com.myjb.dev.model

import com.myjb.dev.model.data.Company
import com.myjb.dev.model.remote.datasource.APIResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getBooks(company: Company, text: String): Flow<APIResponse>
}