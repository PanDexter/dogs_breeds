package com.pandexter.dogsbreed.domain.usecase

import com.pandexter.dogsbreed.common.usecase.NoParametersUseCase
import com.pandexter.dogsbreed.common.usecase.UseCase
import com.pandexter.dogsbreed.domain.repository.DogBreedRepository
import javax.inject.Inject

class GetBreedPhotos @Inject constructor(private val repository: DogBreedRepository) :
    UseCase<Unit, String>() {

    override suspend fun run(breed: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}