package com.pandexter.dogsbreed.data.repository

import com.pandexter.dogsbreed.common.runRecoverCatching
import com.pandexter.dogsbreed.data.local.dao.BreedsDao
import com.pandexter.dogsbreed.data.local.dao.FavouriteBreedPhotosDao
import com.pandexter.dogsbreed.data.local.entity.BreedEntity
import com.pandexter.dogsbreed.data.local.entity.FavouritePhotoEntity
import com.pandexter.dogsbreed.data.remote.DogsService
import com.pandexter.dogsbreed.domain.model.Breed
import com.pandexter.dogsbreed.domain.model.Photo
import com.pandexter.dogsbreed.domain.repository.DogRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepositoryImpl @Inject constructor(
    private val service: DogsService,
    private val favouriteBreedPhotosDao: FavouriteBreedPhotosDao,
    private val breedsDao: BreedsDao
) : DogRepository {

    override suspend fun getAllBreeds(): Result<List<Breed>> = runRecoverCatching {
        val breedList = service.getAllBreeds().data.map { (breed, subBreeds) ->
            Breed(breed, subBreeds)
        }
        breedList
    }.onSuccess {
        it.saveAllBreeds()
    }

    private fun List<Breed>.saveAllBreeds() {
        breedsDao.insertAll(map {
            BreedEntity(0, it.name, it.subBreeds)
        })
    }

    override suspend fun getAllBreedsLocal(): Result<List<Breed>> = runRecoverCatching {
        breedsDao.getAllBreeds().map {
            Breed(it.name, it.subBreeds)
        }
    }

    override suspend fun getBreedPhotos(breed: String): Result<List<String>> = runRecoverCatching {
        service.getBreedPhotos(breed).photos
    }

    override suspend fun getFavouritePhotos(): Result<List<Photo>> = runRecoverCatching {
        favouriteBreedPhotosDao.getAllPhotos().map {
            Photo(it.photoUrl, it.breedName, isFavourite = true)
        }
    }

    override suspend fun getBreedFavouritePhotos(breed: String): Result<List<Photo>> =
        runRecoverCatching {
            favouriteBreedPhotosDao.getPhotosByBreed(breed).map {
                Photo(it.photoUrl, it.breedName, isFavourite = true)
            }
        }

    override suspend fun addFavouritePhoto(photo: Photo): Result<Unit> = runRecoverCatching {
        favouriteBreedPhotosDao.insert(FavouritePhotoEntity(0, photo.url, photo.breedName))
    }

    override suspend fun removeFavouritePhoto(photoUrl: String) = runRecoverCatching {
        favouriteBreedPhotosDao.delete(photoUrl)
    }
}