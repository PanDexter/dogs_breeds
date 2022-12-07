package com.pandexter.dogsbreed.ui.favouritePhotos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.pandexter.dogsbreed.R
import com.pandexter.dogsbreed.databinding.FragmentFavouritePhotosBinding
import com.pandexter.dogsbreed.ui.favouritePhotos.model.BreedSelector
import com.pandexter.dogsbreed.ui.breedDetails.PhotosAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritePhotosFragment : Fragment() {

    private var _binding: FragmentFavouritePhotosBinding? = null
    private val binding get() = _binding ?: throw Exception()
    private lateinit var favouritePhotosAdapter: FavouritePhotosAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<BreedSelector>
    private val viewModel: FavouritePhotosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritePhotosBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupView() {
        with(binding.rvBreeds) {
            favouritePhotosAdapter = FavouritePhotosAdapter() { photo, _ ->
                viewModel.removePhotoFromFavourite(photo)
            }
            adapter = favouritePhotosAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)

        }
    }

    private fun setupSpinner(breedList: List<BreedSelector>?) {
        spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            R.id.tv_breed,
            breedList ?: listOf(BreedSelector.All)
        )
        binding.spinnerBreeds.adapter = spinnerAdapter
        binding.spinnerBreeds.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val filteredList = when (val spinnerData =
                    binding.spinnerBreeds.adapter.getItem(position) as BreedSelector) {
                    BreedSelector.All -> viewModel.state.value.data
                    is BreedSelector.Breed -> viewModel.state.value.data?.filter { it.breedName == spinnerData.name }
                }
                favouritePhotosAdapter.submitList(filteredList)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }
    }


    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                favouritePhotosAdapter.submitList(it.data)
                setupSpinner(it.breedsList)
            }
        }
    }
}