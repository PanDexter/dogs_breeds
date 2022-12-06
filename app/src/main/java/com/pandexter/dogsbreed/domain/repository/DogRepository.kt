package com.pandexter.dogsbreed.domain.repository

import com.pandexter.dogsbreed.domain.model.Breed
import com.pandexter.dogsbreed.domain.model.Photo

interface DogRepository {
    suspend fun getAllBreeds(): Result<List<Breed>>
    suspend fun getAllBreedsLocal(): Result<List<Breed>>
    suspend fun getBreedPhotos(breed: String): Result<List<String>>
    suspend fun getFavouritePhotos(): Result<List<Photo>>
    suspend fun getBreedFavouritePhotos(breed: String): Result<List<Photo>>
    suspend fun addFavouritePhoto(photo: Photo): Result<Unit>
    suspend fun removeFavouritePhoto(photoUrl: String): Result<Unit>
}