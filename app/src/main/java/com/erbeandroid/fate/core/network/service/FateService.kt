package com.erbeandroid.fate.core.network.service

import com.erbeandroid.fate.core.network.model.ParseResponse
import com.erbeandroid.fate.core.network.model.ServantResponse
import retrofit2.http.GET

interface FateService {

    @GET("export/NA/nice_servant.json")
    suspend fun getServants(): List<ServantResponse>

    @GET("api.php?action=parse&format=json&page=Event_List&formatversion=latest")
    suspend fun getEvents(): ParseResponse
}