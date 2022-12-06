package com.pandexter.dogsbreed.domain.usecase

import com.pandexter.dogsbreed.common.usecase.NoParametersUseCase
import com.pandexter.dogsbreed.domain.model.Breed
import com.pandexter.dogsbreed.domain.repository.DogRepository
import javax.inject.Inject

class GetAllBreedsUseCase @Inject constructor(private val repository: DogRepository) :
    NoParametersUseCase<List<Breed>>() {

    override suspend fun run(): Result<List<Breed>> {
        val breedsApi = repository.getAllBreeds()
        val breedsLocal = repository.getAllBreedsLocal()

        return if (breedsApi.isFailure) {
            breedsLocal
        } else {
            breedsApi
        }
    }
}