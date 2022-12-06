package com.pandexter.dogsbreed.domain.usecase

import com.pandexter.dogsbreed.common.usecase.UseCase
import com.pandexter.dogsbreed.domain.model.Photo
import com.pandexter.dogsbreed.domain.repository.DogRepository
import javax.inject.Inject

class AddFavouritePhotoUseCase @Inject constructor(private val repository: DogRepository) :
    UseCase<Unit, Photo>() {

    override suspend fun run(photo: Photo): Result<Unit> =
        repository.addFavouritePhoto(photo)
}