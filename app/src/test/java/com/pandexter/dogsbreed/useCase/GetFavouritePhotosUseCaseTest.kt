package com.pandexter.dogsbreed.useCase

import com.pandexter.dogsbreed.domain.model.Photo
import com.pandexter.dogsbreed.domain.repository.DogRepository
import com.pandexter.dogsbreed.domain.usecase.GetFavouritePhotosUseCase
import com.pandexter.dogsbreed.util.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetFavouritePhotosUseCaseTest : BaseTest() {

    private val repository: DogRepository = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when get favourite photos it should return all favourite photos`() =
        runTest(StandardTestDispatcher()) {
            val breed = "Shiba Inu"
            val expectedResponse = listOf(
                Photo("some url", breed, isFavourite = true),
                Photo("some other url", breed, isFavourite = true)
            )
            val useCase = GetFavouritePhotosUseCase(repository)

            coEvery { repository.getFavouritePhotos() } returns Result.success(expectedResponse)

            val actualResponse = useCase.run()

            coVerify(exactly = 1) {
                repository.getFavouritePhotos()
            }

            Assert.assertNotNull(actualResponse)
            Assert.assertEquals(expectedResponse, actualResponse.getOrThrow())
        }
}