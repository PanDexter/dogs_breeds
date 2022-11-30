package com.pandexter.dogsbreed.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface DogsService {

    @GET("/api/breeds/list/all")
    suspend fun getAllBreeds()

    @GET("/api/breed/{$BREED}/images")
    suspend fun getBreedPhotos(@Path(BREED) breed: String)

    companion object {
        const val BREED = "breed"
    }
}