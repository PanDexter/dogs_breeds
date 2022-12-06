package com.pandexter.dogsbreed.domain.usecase

import com.pandexter.dogsbreed.common.usecase.UseCase
import com.pandexter.dogsbreed.domain.model.Photo
import com.pandexter.dogsbreed.domain.repository.DogRepository
import javax.inject.Inject

class GetBreedPhotosUseCase @Inject constructor(private val repository: DogRepository) :
    UseCase<List<Photo>, String>() {

    override suspend fun run(breed: String): Result<List<Photo>> {
        val breedPhotos = repository.getBreedPhotos(breed)
        val favouritePhotos = repository.getBreedFavouritePhotos(breed)

        //if breedPhotos isFailure then we want to show only favouritePhotos if there are any
        if (breedPhotos.isFailure) {
            return favouritePhotos
        }

        //if element is in favourite then we want to set [isFavourite] value to true
        val combinedList = breedPhotos.getOrNull()?.toMutableList()?.map { photoUrl ->
            val isInFavourite = favouritePhotos.getOrNull()?.find { it.url == photoUrl }
            Photo(photoUrl, breed, isInFavourite != null)
        }

        return if (combinedList.isNullOrEmpty()) {
            Result.failure(Throwable())
        } else {
            Result.success(combinedList)
        }
    }
}