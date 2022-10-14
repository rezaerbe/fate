package com.erbeandroid.fate.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServantResponse(
    @field:Json(name = "id")
    val id: Int?
)

@JsonClass(generateAdapter = true)
data class ParseResponse(
    @field:Json(name = "parse")
    val eventResponse: EventResponse?
)

@JsonClass(generateAdapter = true)
data class EventResponse(
    @field:Json(name = "text")
    val text: String?
)