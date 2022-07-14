package com.arjun.bluffer.network

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class Image(
    @Json(name = "url") val imageUrl: String?,
    @Json(name = "nsfw") val nsfw: Boolean?
)