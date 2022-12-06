package com.pandexter.dogsbreed.ui.breedDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandexter.dogsbreed.domain.model.Photo
import com.pandexter.dogsbreed.domain.usecase.AddFavouritePhotoUseCase
import com.pandexter.dogsbreed.domain.usecase.GetBreedPhotosUseCase
import com.pandexter.dogsbreed.domain.usecase.RemoveFavouritePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val getBreedPhotos: GetBreedPhotosUseCase,
    private val addFavouritePhotoUseCase: AddFavouritePhotoUseCase,
    private val removeFavouritePhotoUseCase: RemoveFavouritePhotoUseCase
) : ViewModel() {

    private val _breedDetailsState: MutableStateFlow<BreedListState> =
        MutableStateFlow(BreedListState())
    val breedDetailsState: StateFlow<BreedListState>
        get() = _breedDetailsState

    private val breedName
        get() = state.get<String>("breedName") ?: ""

    init {
        getBreedPhotos()
    }

    private fun getBreedPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            getBreedPhotos.invoke(params = breedName) { result ->
                result.onSuccess {
                    _breedDetailsState.update { currentState ->
                        currentState.copy(data = it)
                    }
                }.onFailure {
                    _breedDetailsState.update { currentState ->
                        currentState.copy(data = null)
                    }
                }
            }
        }
    }

    /**
     * If current [Photo.isFavourite] is true then we want to change it to false and vice versa
     */
    fun changePhotoFavouriteState(photo: Photo, position: Int) {
        if (photo.isFavourite) {
            removePhotoFromFavourite(photo, position)
        } else {
            addPhotoToFavourite(photo, position)
        }
    }

    private fun addPhotoToFavourite(photo: Photo, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addFavouritePhotoUseCase.invoke(
                params = photo
            ) { result ->
                result.onSuccess {
                    _breedDetailsState.update { currentState ->
                        upgradePhotoListState(
                            currentState = currentState,
                            photo = photo,
                            position = position,
                            isFavourite = true
                        )
                    }
                }
            }
        }
    }

    private fun removePhotoFromFavourite(photo: Photo, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavouritePhotoUseCase.invoke(params = photo.url) { result ->
                result.onSuccess {
                    _breedDetailsState.update { currentState ->
                        upgradePhotoListState(
                            currentState = currentState,
                            photo = photo,
                            position = position,
                            isFavourite = false
                        )
                    }
                }
            }
        }
    }

    private fun upgradePhotoListState(
        currentState: BreedListState,
        photo: Photo,
        position: Int,
        isFavourite: Boolean
    ): BreedListState {
        val upgradedList = currentState.data?.toMutableList()
        val photoPosition = upgradedList?.indexOfFirst { it.url == photo.url }
        if (photoPosition != null) {
            upgradedList[photoPosition] = Photo(photo.url, photo.breedName, isFavourite)
        }
        return currentState.copy(
            data = upgradedList,
            changedPosition = position
        )
    }

    data class BreedListState(
        val data: List<Photo>? = listOf(),
        val changedPosition: Int? = null
    )
}