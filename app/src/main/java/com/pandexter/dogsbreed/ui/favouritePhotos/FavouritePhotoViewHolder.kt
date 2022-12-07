package com.pandexter.dogsbreed.ui.favouritePhotos

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pandexter.dogsbreed.R
import com.pandexter.dogsbreed.common.capitalizeFirstLetter
import com.pandexter.dogsbreed.databinding.ItemPhotoFavouriteBinding
import com.pandexter.dogsbreed.domain.model.Photo

class FavouritePhotoViewHolder(
    private val binding: ItemPhotoFavouriteBinding, private val onItemClicked: (Photo, Int) -> Unit?
) : RecyclerView.ViewHolder(binding.root) {


    private var item: Photo? = null

    init {
        binding.ivLike.setOnClickListener {
            item?.let { photo ->
                onItemClicked.invoke(photo, bindingAdapterPosition)
            }
        }
        binding.ivPhoto.setOnClickListener {
            item?.let { photo ->
                onItemClicked.invoke(photo, bindingAdapterPosition)
            }
        }
    }

    fun bind(item: Photo) {
        this.item = item
        binding.ivPhoto.load(item.url)
        binding.ivLike.load(if (item.isFavourite) R.drawable.ic_favourite_filled else R.drawable.ic_favourite)
        binding.tvBreed.text = item.breedName.capitalizeFirstLetter()
    }
}