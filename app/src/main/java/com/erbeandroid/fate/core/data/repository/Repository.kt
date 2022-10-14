package com.erbeandroid.fate.core.data.repository

import com.erbeandroid.fate.core.data.model.Event
import com.erbeandroid.fate.core.data.model.Servant
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getServants(): Flow<List<Servant>>

    suspend fun getEvents(): Flow<Event?>
}