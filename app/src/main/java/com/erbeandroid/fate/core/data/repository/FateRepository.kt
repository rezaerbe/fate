package com.erbeandroid.fate.core.data.repository

import com.erbeandroid.fate.core.common.dispatcher.Dispatcher
import com.erbeandroid.fate.core.common.dispatcher.FateDispatcher.Default
import com.erbeandroid.fate.core.data.model.Event
import com.erbeandroid.fate.core.data.model.Servant
import com.erbeandroid.fate.core.data.model.asDomain
import com.erbeandroid.fate.core.network.datasource.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FateRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher
) : Repository {

    override suspend fun getServants(): Flow<List<Servant>> =
        remoteDataSource.getServants()
            .map { servantsResponse ->
                servantsResponse.map { servantResponse ->
                    servantResponse.asDomain()
                }
            }.flowOn(defaultDispatcher)

    override suspend fun getEvents(): Flow<Event?> =
        remoteDataSource.getEvents()
            .map { parseResponse ->
                parseResponse.eventResponse?.asDomain()
            }.flowOn(defaultDispatcher)
}