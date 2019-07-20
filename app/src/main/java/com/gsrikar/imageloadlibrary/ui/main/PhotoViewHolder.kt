package com.gsrikar.imageloadlibrary.ui.main

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.image_load_item.view.*

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val photoImageView: AppCompatImageView = itemView.photoImageView

}