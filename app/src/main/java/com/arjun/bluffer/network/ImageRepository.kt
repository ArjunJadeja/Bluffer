package com.arjun.bluffer.network

class ImageRepository {
    suspend fun getRandomImage(): Image {
        return ImageApi.retrofitService.getRandomImage()
    }
}