package com.erbeandroid.fate.core.data.model

import com.erbeandroid.fate.core.network.model.EventResponse
import com.erbeandroid.fate.core.network.model.ServantResponse

fun ServantResponse.asDomain() = Servant(id = id)

fun EventResponse.asDomain() = Event(text = text)