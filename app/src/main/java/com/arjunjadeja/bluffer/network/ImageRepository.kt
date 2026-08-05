package com.arjunjadeja.bluffer.network

class ImageRepository {
    suspend fun getRandomImage(): Image {
        return ImageApi.retrofitService.getRandomImage()
    }
}