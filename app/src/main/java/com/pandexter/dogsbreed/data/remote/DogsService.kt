package com.pandexter.dogsbreed.data.remote

import com.pandexter.dogsbreed.data.remote.model.BreedPhotosDto
import com.pandexter.dogsbreed.data.remote.model.BreedsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DogsService {

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): BreedsDto

    @GET("breed/{$BREED}/images")
    suspend fun getBreedPhotos(@Path(BREED) breed: String): BreedPhotosDto

    companion object {
        const val BREED = "breed"
    }
}