package com.pandexter.dogsbreed.ui.breedList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandexter.dogsbreed.R
import com.pandexter.dogsbreed.databinding.FragmentBreedListBinding
import com.pandexter.dogsbreed.domain.model.Breed
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BreedListFragment : Fragment() {

    private var _binding: FragmentBreedListBinding? = null
    private val binding get() = _binding ?: throw Exception()

    private val viewModel: BreedListViewModel by viewModels()
    private lateinit var breedAdapter: BreedsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreedListBinding.inflate(inflater, container, false)
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
        with(binding.rvBreeds) {
            breedAdapter = BreedsAdapter { breed ->
                navigateToBreedDetails(breed)
            }
            adapter = breedAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val divider = DividerItemDecoration(
                context,
                VERTICAL
            ).apply {
                ContextCompat.getDrawable(context, R.drawable.line_divider)?.let { setDrawable(it) }
            }
            addItemDecoration(divider)
        }

        binding.fbFavourite.setOnClickListener {
            findNavController().navigate(R.id.navigateToFavouritePhotos)
        }
    }

    private fun observeBreedState() {
        lifecycleScope.launchWhenStarted {
            viewModel.breedState.collect {
                breedAdapter.submitList(it.data)
            }
        }
    }

    private fun navigateToBreedDetails(breed: Breed) {
        val action = BreedListFragmentDirections.navigateToBreedDetails(breed.name)
        findNavController().navigate(action)
    }
}