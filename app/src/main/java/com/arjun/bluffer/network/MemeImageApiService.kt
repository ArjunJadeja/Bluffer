package com.arjun.bluffer.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://meme-api.herokuapp.com/gimme/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MemeImageApiService {
    @GET("hmmm")
    suspend fun getRandomPhoto(): MemeImage
}

object MemeImageApi {
    val retrofitService: MemeImageApiService by lazy {
        retrofit.create((MemeImageApiService::class.java))
    }
}
