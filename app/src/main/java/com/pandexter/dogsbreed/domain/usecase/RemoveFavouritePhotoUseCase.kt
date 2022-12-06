package com.pandexter.dogsbreed.domain.usecase

import com.pandexter.dogsbreed.common.usecase.UseCase
import com.pandexter.dogsbreed.domain.repository.DogRepository
import javax.inject.Inject

class RemoveFavouritePhotoUseCase @Inject constructor(private val repository: DogRepository) :
    UseCase<Unit, String>() {

    override suspend fun run(photoUrl: String): Result<Unit> =
        repository.removeFavouritePhoto(photoUrl)
}