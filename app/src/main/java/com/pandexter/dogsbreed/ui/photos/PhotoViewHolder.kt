package com.pandexter.dogsbreed.ui.photos

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pandexter.dogsbreed.R
import com.pandexter.dogsbreed.databinding.ItemPhotoBinding
import com.pandexter.dogsbreed.domain.model.Photo

class PhotoViewHolder(
    private val binding: ItemPhotoBinding, private val onItemClicked: (Photo, Int) -> Unit?
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
    }

    fun bindFavouriteState(item: Photo) {
        this.item = item
        binding.ivLike.load(if (item.isFavourite) R.drawable.ic_favourite_filled else R.drawable.ic_favourite)
    }
}