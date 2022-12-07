package com.pandexter.dogsbreed.useCase

import com.pandexter.dogsbreed.domain.model.Breed
import com.pandexter.dogsbreed.domain.repository.DogRepository
import com.pandexter.dogsbreed.domain.usecase.GetAllBreedsUseCase
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
class GetAllBreedsUseCaseTest : BaseTest() {

    private val repository: DogRepository = mockk()

    @Test
    fun `when get all breeds from api it should return breed list`() =
        runTest(StandardTestDispatcher()) {
            val expectedResponse = breedList
            val useCase = GetAllBreedsUseCase(repository)

            coEvery { repository.getAllBreeds() } returns Result.success(breedList)
            coEvery { repository.getAllBreedsLocal() } returns Result.failure(Throwable())

            val actualResponse = useCase.run()

            coVerify(exactly = 1) {
                repository.getAllBreeds()
            }

            Assert.assertNotNull(actualResponse)
            Assert.assertEquals(expectedResponse, actualResponse.getOrThrow())
        }

    @Test
    fun `when get all breeds and no network it should return breed list from database`() =
        runTest(StandardTestDispatcher()) {
            val expectedLocalResponse = breedList
            val useCase = GetAllBreedsUseCase(repository)

            coEvery { repository.getAllBreeds() } returns Result.failure(Throwable())
            coEvery { repository.getAllBreedsLocal() } returns Result.success(expectedLocalResponse)

            val actualResponse = useCase.run()

            coVerify(exactly = 1) {
                repository.getAllBreeds()
                repository.getAllBreedsLocal()
            }

            Assert.assertNotNull(actualResponse)
            Assert.assertEquals(expectedLocalResponse, actualResponse.getOrThrow())
        }

    @Test
    fun `when get all breeds and no network and no database it should fail`() =
        runTest(StandardTestDispatcher()) {
            val useCase = GetAllBreedsUseCase(repository)

            coEvery { repository.getAllBreeds() } returns Result.failure(Throwable())
            coEvery { repository.getAllBreedsLocal() } returns Result.failure(Throwable())

            val actualResponse = useCase.run()

            coVerify(exactly = 1) {
                repository.getAllBreeds()
                repository.getAllBreedsLocal()
            }

            Assert.assertNotNull(actualResponse)
        }


    private val breedList =
        listOf(Breed("Akita", emptyList()), Breed("Shiba Inu", emptyList()))
}