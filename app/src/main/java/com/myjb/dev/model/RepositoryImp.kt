package com.myjb.dev.model

import com.myjb.dev.model.data.Company
import com.myjb.dev.model.remote.datasource.APIResponse
import com.myjb.dev.model.remote.datasource.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RepositoryImp(
    private val aladinDataSource: DataSource,
    private val yes24DataSource: DataSource,
) : Repository {
    override suspend fun getBooks(company: Company, text: String): Flow<APIResponse> {
        return when (company) {
            Company.ALADIN -> {
                aladinDataSource.getBooks(text = text)
            }

            Company.YES24 -> {
                yes24DataSource.getBooks(text = text)
            }

            else -> {
                flowOf(APIResponse.Error(errorCode = 0, message = ""))
            }
        }
    }
}