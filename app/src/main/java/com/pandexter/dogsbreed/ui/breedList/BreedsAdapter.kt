package com.pandexter.dogsbreed.ui.breedList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pandexter.dogsbreed.databinding.ItemBreedBinding
import com.pandexter.dogsbreed.domain.model.Breed

class BreedsAdapter(
    private inline val onItemClicked: (Breed) -> Unit = {}
) : ListAdapter<Breed, BreedViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder =
        BreedViewHolder(
            ItemBreedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked
        )

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Breed>() {
            override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean =
                oldItem.name == newItem.name && oldItem.subBreeds == newItem.subBreeds

            override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean =
                oldItem == newItem
        }
    }
}