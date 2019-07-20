package com.gsrikar.imageloadlibrary.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.gsrikar.imagelibrary.ImageLibrary
import com.gsrikar.imageloadlibrary.R

class PhotoAdapter(private val urls: ArrayList<String>) : RecyclerView.Adapter<PhotoViewHolder>() {

    private val imageLibrary = ImageLibrary()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_load_item, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int = urls.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        loadImage(holder.photoImageView, urls[position], position)
    }

    private fun loadImage(imageView: AppCompatImageView, url: String, position: Int) {
        // Load the image on the image view intelligently
        imageLibrary.loadImage(imageView, url, position)
    }

    override fun onViewRecycled(holder: PhotoViewHolder) {
        super.onViewRecycled(holder)
        // Skip loading the image for the position
        imageLibrary.recycledView(holder.adapterPosition)
    }
}