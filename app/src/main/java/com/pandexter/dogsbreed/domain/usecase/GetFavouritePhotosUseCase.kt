package com.pandexter.dogsbreed.domain.usecase

import com.pandexter.dogsbreed.common.usecase.NoParametersUseCase
import com.pandexter.dogsbreed.domain.model.Photo
import com.pandexter.dogsbreed.domain.repository.DogRepository
import javax.inject.Inject

class GetFavouritePhotosUseCase @Inject constructor(private val repository: DogRepository) :
    NoParametersUseCase<List<Photo>>() {

    override suspend fun run(): Result<List<Photo>> = repository.getFavouritePhotos()
}