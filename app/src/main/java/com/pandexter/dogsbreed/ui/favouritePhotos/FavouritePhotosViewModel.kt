package com.pandexter.dogsbreed.ui.favouritePhotos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandexter.dogsbreed.domain.model.Photo
import com.pandexter.dogsbreed.domain.usecase.GetFavouritePhotosUseCase
import com.pandexter.dogsbreed.domain.usecase.RemoveFavouritePhotoUseCase
import com.pandexter.dogsbreed.ui.favouritePhotos.model.BreedSelector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritePhotosViewModel @Inject constructor(
    private val getFavouritePhotosUseCase: GetFavouritePhotosUseCase,
    private val removeFavouritePhotoUseCase: RemoveFavouritePhotoUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<FavouritePhotosState> =
        MutableStateFlow(FavouritePhotosState())
    val state: StateFlow<FavouritePhotosState>
        get() = _state

    init {
        getFavouriteData()
    }

    private fun getFavouriteData() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavouritePhotosUseCase.invoke { result ->
                result.onSuccess {
                    _state.update { currentState ->
                        currentState.copy(data = it, breedsList = determineBreedsList(it))
                    }
                }.onFailure {
                    _state.update { currentState ->
                        currentState.copy(data = null)
                    }
                }
            }
        }
    }

    private fun determineBreedsList(breedList: List<Photo>?) =
        buildList {
            add(BreedSelector.All)
            breedList?.map { BreedSelector.Breed(it.breedName) }?.distinct()?.let { addAll(it) }
        }

    fun removePhotoFromFavourite(photo: Photo) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavouritePhotoUseCase.invoke(params = photo.url) { result ->
                result.onSuccess {
                    _state.update { currentState ->
                        val updatedList = currentState.data?.toMutableList()
                        val photoPosition = updatedList?.indexOfFirst { it.url == photo.url }
                        if (photoPosition != null) {
                            updatedList.removeAt(photoPosition)
                        }
                        currentState.copy(
                            data = updatedList?.toList(),
                            breedsList = determineBreedsList(updatedList),
                        )
                    }
                }
            }
        }
    }

    data class FavouritePhotosState(
        val data: List<Photo>? = emptyList(),
        val breedsList: List<BreedSelector>? = emptyList(),
    )
}