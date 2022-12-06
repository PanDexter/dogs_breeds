package com.pandexter.dogsbreed.ui.breedList

import androidx.recyclerview.widget.RecyclerView
import com.pandexter.dogsbreed.common.capitalizeFirstLetter
import com.pandexter.dogsbreed.databinding.ItemBreedBinding
import com.pandexter.dogsbreed.domain.model.Breed

class BreedViewHolder(
    private val binding: ItemBreedBinding,
    private val onItemClicked: (Breed) -> Unit? = {}
) : RecyclerView.ViewHolder(binding.root) {

    private var item: Breed? = null

    init {
        binding.root.setOnClickListener {
            item?.let(onItemClicked::invoke)
        }
    }

    fun bind(item: Breed) {
        this.item = item
        binding.name = item.name.capitalizeFirstLetter()
    }
}