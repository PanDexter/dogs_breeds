package com.pandexter.dogsbreed.useCase

import com.pandexter.dogsbreed.domain.model.Photo
import com.pandexter.dogsbreed.domain.repository.DogRepository
import com.pandexter.dogsbreed.domain.usecase.GetBreedPhotosUseCase
import com.pandexter.dogsbreed.util.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetBreedPhotosUseCaseTest : BaseTest() {

    private val repository: DogRepository = mockk()

    @Test
    fun `when get breed photo then it should return photo list`() =
        runTest(StandardTestDispatcher()) {
            val breed = "Shiba Inu"
            val photoList = photoList
            val expectedResponse = listOf(
                Photo(photoList[0], breed, isFavourite = false),
                Photo(photoList[1], breed, isFavourite = false)
            )
            val useCase = GetBreedPhotosUseCase(repository)

            coEvery { repository.getBreedPhotos(breed) } returns Result.success(photoList)
            coEvery { repository.getBreedFavouritePhotos(breed) } returns Result.success(emptyList())

            val actualResponse = useCase.run(breed = breed)

            coVerify(exactly = 1) {
                repository.getBreedPhotos(breed = breed)
            }

            Assert.assertNotNull(actualResponse)
            Assert.assertEquals(expectedResponse, actualResponse.getOrThrow())
        }

    @Test
    fun `when get breed photo then and have some in favourite it should return photo list with favourite`() =
        runTest(StandardTestDispatcher()) {
            val breed = "Shiba Inu"
            val photoList = photoList
            val favouritePhoto = Photo(photoList[0], breed, isFavourite = true)
            val expectedResponse = listOf(
                favouritePhoto,
                Photo(photoList[1], breed, isFavourite = false)
            )
            val useCase = GetBreedPhotosUseCase(repository)

            coEvery { repository.getBreedPhotos(breed) } returns Result.success(photoList)
            coEvery { repository.getBreedFavouritePhotos(breed) } returns Result.success(
                listOf(favouritePhoto)
            )

            val actualResponse = useCase.run(breed = breed)

            coVerify(exactly = 1) {
                repository.getBreedPhotos(breed = breed)
                repository.getBreedFavouritePhotos(breed = breed)
            }

            Assert.assertNotNull(actualResponse)
            Assert.assertEquals(expectedResponse, actualResponse.getOrThrow())
        }

    private val photoList = listOf("some url", "some other url")
}