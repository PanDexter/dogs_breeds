package com.pandexter.dogsbreed.ui.favouritePhotos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pandexter.dogsbreed.databinding.ItemPhotoFavouriteBinding
import com.pandexter.dogsbreed.domain.model.Photo

class FavouritePhotosAdapter(
    private val onItemClicked: (Photo, Int) -> Unit?
) : ListAdapter<Photo, FavouritePhotoViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritePhotoViewHolder =
        FavouritePhotoViewHolder(
            ItemPhotoFavouriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked
        )

    override fun onBindViewHolder(holder: FavouritePhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(
                oldItem: Photo,
                newItem: Photo
            ): Boolean =
                oldItem.url == newItem.url && oldItem.isFavourite == newItem.isFavourite

            override fun areContentsTheSame(
                oldItem: Photo,
                newItem: Photo
            ): Boolean =
                oldItem == newItem
        }
    }
}