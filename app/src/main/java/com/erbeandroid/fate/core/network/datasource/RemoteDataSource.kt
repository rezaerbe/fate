package com.erbeandroid.fate.core.network.datasource

import com.erbeandroid.fate.core.network.model.ParseResponse
import com.erbeandroid.fate.core.network.model.ServantResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun getServants(): Flow<List<ServantResponse>>

    suspend fun getEvents(): Flow<ParseResponse>
}