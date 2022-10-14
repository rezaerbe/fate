package com.erbeandroid.fate.core.network.datasource

import com.erbeandroid.fate.core.common.dispatcher.Dispatcher
import com.erbeandroid.fate.core.common.dispatcher.FateDispatcher.IO
import com.erbeandroid.fate.core.network.model.ParseResponse
import com.erbeandroid.fate.core.network.model.ServantResponse
import com.erbeandroid.fate.core.network.service.FateService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FateRemoteDataSource @Inject constructor(
    private val fateService: FateService,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : RemoteDataSource {

    override suspend fun getServants(): Flow<List<ServantResponse>> =
        flow {
            val servants = fateService.getServants()
            emit(servants)
        }.flowOn(ioDispatcher)

    override suspend fun getEvents(): Flow<ParseResponse> =
        flow {
            val events = fateService.getEvents()
            emit(events)
        }.flowOn(ioDispatcher)
}