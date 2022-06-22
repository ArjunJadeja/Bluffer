package com.arjun.bluffer.network

import com.squareup.moshi.Json

data class MemeImage(
    @Json(name = "url") val imageUrl: String?,
    @Json(name = "author") val statusResponse: String?
)