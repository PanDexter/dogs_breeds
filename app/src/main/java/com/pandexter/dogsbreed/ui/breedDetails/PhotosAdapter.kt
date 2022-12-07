package com.pandexter.dogsbreed.ui.breedDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pandexter.dogsbreed.databinding.ItemPhotoBinding
import com.pandexter.dogsbreed.domain.model.Photo

class PhotosAdapter(
    private val onItemClicked: (Photo, Int) -> Unit?
) : ListAdapter<Photo, PhotoViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked
        )

    override fun onBindViewHolder(
        holder: PhotoViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            holder.bind(getItem(position))
        } else {
            val data = (payloads[0] as Photo)
            holder.bindFavouriteState(data)
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
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

            override fun getChangePayload(oldItem: Photo, newItem: Photo): Any? =
                if (oldItem.isFavourite != newItem.isFavourite) true else null
        }
    }
}