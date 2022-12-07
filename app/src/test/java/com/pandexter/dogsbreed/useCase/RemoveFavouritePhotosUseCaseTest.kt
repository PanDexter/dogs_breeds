package com.pandexter.dogsbreed.useCase

import com.pandexter.dogsbreed.domain.repository.DogRepository
import com.pandexter.dogsbreed.domain.usecase.RemoveFavouritePhotoUseCase
import com.pandexter.dogsbreed.util.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class RemoveFavouritePhotosUseCaseTest : BaseTest() {

    private val repository: DogRepository = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when remove from favourite then it should return nothing`() =
        runTest(StandardTestDispatcher()) {
            val params = "some url"
            val expectedResponse = Unit
            val useCase = RemoveFavouritePhotoUseCase(repository)

            coEvery { repository.removeFavouritePhoto(params) } returns Result.success(Unit)

            val actualResponse = useCase.run(photoUrl = params)

            coVerify(exactly = 1) {
                repository.removeFavouritePhoto(params)
            }

            Assert.assertNotNull(actualResponse)
            Assert.assertEquals(expectedResponse, actualResponse.getOrThrow())
        }
}