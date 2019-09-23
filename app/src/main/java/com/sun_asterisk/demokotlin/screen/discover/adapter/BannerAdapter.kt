package com.sun_asterisk.demokotlin.screen.discover.adapter


import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.utils.OnItemClickListener
import kotlinx.android.synthetic.main.item_banner.view.*

class BannerAdapter constructor(context: Context) : PagerAdapter(), View.OnClickListener {
    private var mContext: Context = context
    private var movies: MutableList<Movie>? = mutableListOf()
    private var mListener: OnItemClickListener? = null
    private var mPosition: Int = 0

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_banner, null)
        onBindView(view, position)
        container.addView(view)
        view.setOnClickListener(this)
        return view
    }

    override fun getCount(): Int {
        return if (movies != null) movies!!.size else 0
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun updateData(moveList: List<Movie>) {
        movies!!.clear()
        movies!!.addAll(moveList)
        notifyDataSetChanged()
    }

    private fun onBindView(view: View, position: Int) {
        view.textViewBannerHome.text = movies!![position].title
        Glide.with(view.context).load("https://image.tmdb.org/t/p/w780" + movies!![position].backdropPath)
            .into(view.imageBanner)
    }

    fun setCurrentPosition(position: Int) {
        mPosition = position
    }

    override fun onClick(v: View) {
        mListener!!.onItemRecyclerViewClick(movies!![mPosition])
    }
}