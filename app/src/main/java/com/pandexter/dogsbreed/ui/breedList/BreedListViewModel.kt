package com.pandexter.dogsbreed.ui.breedList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandexter.dogsbreed.domain.model.Breed
import com.pandexter.dogsbreed.domain.usecase.GetAllBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedListViewModel @Inject constructor(private val getAllBreedsUseCase: GetAllBreedsUseCase) :
    ViewModel() {

    private val _breedState: MutableStateFlow<BreedListState> = MutableStateFlow(BreedListState())
    val breedState: StateFlow<BreedListState>
        get() = _breedState

    init {
        getAllBreeds()
    }

    private fun getAllBreeds() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllBreedsUseCase.invoke { result ->
                result.onSuccess {
                    _breedState.update { currentState ->
                        currentState.copy(data = it)
                    }
                }.onFailure {
                    _breedState.update { currentState ->
                        currentState.copy(data = null)
                    }
                }
            }
        }
    }

    data class BreedListState(
        val data: List<Breed>? = emptyList()
    )
}