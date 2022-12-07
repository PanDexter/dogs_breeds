package com.pandexter.dogsbreed.useCase

import com.pandexter.dogsbreed.domain.model.Photo
import com.pandexter.dogsbreed.domain.repository.DogRepository
import com.pandexter.dogsbreed.domain.usecase.AddFavouritePhotoUseCase
import com.pandexter.dogsbreed.util.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class AddFavouritePhotoUseCaseTest : BaseTest() {

    private val repository: DogRepository = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when add photo to favourite then it should return nothing`() =
        runTest(StandardTestDispatcher()) {
            val params = Photo("", "", true)
            val expectedResponse = Unit
            val useCase = AddFavouritePhotoUseCase(repository)

            coEvery { repository.addFavouritePhoto(params) } returns Result.success(Unit)

            val actualResponse = useCase.run(photo = params)

            coVerify(exactly = 1) {
                repository.addFavouritePhoto(params)
            }

            Assert.assertNotNull(actualResponse)
            Assert.assertEquals(expectedResponse, actualResponse.getOrThrow())
        }
}