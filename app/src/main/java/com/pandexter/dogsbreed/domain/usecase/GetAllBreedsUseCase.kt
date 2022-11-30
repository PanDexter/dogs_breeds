package com.pandexter.dogsbreed.domain.usecase

import com.pandexter.dogsbreed.common.usecase.NoParametersUseCase
import com.pandexter.dogsbreed.domain.repository.DogBreedRepository
import javax.inject.Inject

class GetAllBreedsUseCase @Inject constructor(private val repository: DogBreedRepository) :
    NoParametersUseCase<Unit>() {

    override suspend fun run(): Result<Unit> {
        TODO("Not yet implemented")
    }
}