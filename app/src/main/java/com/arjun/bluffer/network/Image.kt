package com.arjun.bluffer.network

import com.squareup.moshi.Json

data class Image(
    @Json(name = "url") val imageUrl: String?,
    @Json(name = "nsfw") val nsfw: Boolean?
)