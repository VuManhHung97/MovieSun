package com.sun_asterisk.demokotlin.screen.detailmovie.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Video
import com.sun_asterisk.demokotlin.utils.BaseAdapter
import com.sun_asterisk.demokotlin.utils.BaseViewHolder
import com.sun_asterisk.demokotlin.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.item_trailers.view.imagePosterTrailers

class VideoAdapter : BaseAdapter<Video>() {
    override fun viewHolder(view: View): BaseViewHolder<Video> = VideoAdapterViewHolder(view)

    override fun layout(row: Int): Int = R.layout.item_trailers

    companion object {
        class VideoAdapterViewHolder(
            private val view: View
        ) : BaseViewHolder<Video>(view) {

            override fun bindData(data: Video, listener: OnItemRecyclerViewClickListener<Video>) {
                Glide.with(view.context).load("https://i.ytimg.com/vi/${data.key}/mqdefault.jpg")
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(16)))
                    .into(view.imagePosterTrailers)
                view.setOnClickListener { listener.onItemClick(data) }
            }
        }
    }
}