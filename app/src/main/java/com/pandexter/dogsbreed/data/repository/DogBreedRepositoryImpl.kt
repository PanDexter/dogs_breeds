package com.pandexter.dogsbreed.data.repository

import com.pandexter.dogsbreed.data.remote.DogsService
import com.pandexter.dogsbreed.domain.repository.DogBreedRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogBreedRepositoryImpl @Inject constructor(
    private val service: DogsService
) : DogBreedRepository {

}