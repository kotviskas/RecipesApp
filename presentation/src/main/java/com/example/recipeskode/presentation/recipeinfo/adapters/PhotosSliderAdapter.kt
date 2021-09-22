package com.example.recipeskode.presentation.recipeinfo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.recipeskode.presentation.databinding.ImageLayoutBinding
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso


class PhotosSliderAdapter(var listener: OnPhotoClick) :
    SliderViewAdapter<PhotosSliderAdapter.SliderAdapterVH>() {
    private var mSliderItems: MutableList<String> = ArrayList()

    interface OnPhotoClick {
        fun itemClick(url: String)
    }

    fun renewItems(sliderItems: MutableList<String>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(url: String) {
        mSliderItems.add(url)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val binding = ImageLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderAdapterVH(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val url: String = mSliderItems[position]
        Picasso.get()
            .load(url)
            .into(viewHolder.binding.photoImageView)
        viewHolder.binding.photoImageView.setOnClickListener {
            listener.itemClick(url)
        }
    }


    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    inner class SliderAdapterVH(val binding: ImageLayoutBinding) : ViewHolder(binding.root) {
    }

}