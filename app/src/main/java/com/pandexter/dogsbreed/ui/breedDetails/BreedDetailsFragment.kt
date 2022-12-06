package com.pandexter.dogsbreed.ui.breedDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.pandexter.dogsbreed.databinding.FragmentBreedDetailsBinding
import com.pandexter.dogsbreed.ui.photos.PhotosAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedDetailsFragment : Fragment() {

    private var _binding: FragmentBreedDetailsBinding? = null
    private val binding get() = _binding ?: throw Exception()

    private val viewModel: BreedDetailsViewModel by viewModels()
    private lateinit var photosAdapter: PhotosAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreedDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeBreedState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupView() {
        with(binding.rvPhotos) {
            photosAdapter = PhotosAdapter { photo, position ->
                viewModel.changePhotoFavouriteState(photo, position)
            }
            adapter = photosAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 3)
        }
    }

    private fun observeBreedState() {
        lifecycleScope.launchWhenStarted {
            viewModel.breedDetailsState.collect {
                val changedPosition = it.changedPosition
                if (photosAdapter.currentList.isNotEmpty() && changedPosition != null) {
                    photosAdapter.notifyItemChanged(changedPosition, it.data?.get(changedPosition))
                } else {
                    photosAdapter.submitList(it.data)
                }
            }
        }
    }
}