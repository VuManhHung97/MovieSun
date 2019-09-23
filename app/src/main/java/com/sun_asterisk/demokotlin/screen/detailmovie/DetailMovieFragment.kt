package com.sun_asterisk.demokotlin.screen.detailmovie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.model.Video
import com.sun_asterisk.demokotlin.data.source.MovieRepository
import com.sun_asterisk.demokotlin.data.source.local.MovieDatabase
import com.sun_asterisk.demokotlin.data.source.local.MovieLocalDataSource
import com.sun_asterisk.demokotlin.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.demokotlin.screen.detailmovie.adapter.VideoAdapter
import com.sun_asterisk.demokotlin.screen.discover.DiscoverViewMode
import com.sun_asterisk.demokotlin.utils.FragmentHelper
import com.sun_asterisk.demokotlin.utils.MyViewModelFactory
import com.sun_asterisk.demokotlin.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.demokotlin.utils.loadImageUrl
import kotlinx.android.synthetic.main.fragement_detail_movie.imageBackdropPath
import kotlinx.android.synthetic.main.fragement_detail_movie.imageFavorite
import kotlinx.android.synthetic.main.fragement_detail_movie.imagePosterPath
import kotlinx.android.synthetic.main.fragement_detail_movie.ratingBarDetail
import kotlinx.android.synthetic.main.fragement_detail_movie.recyclerViewTrailers
import kotlinx.android.synthetic.main.fragement_detail_movie.textReleaseDate
import kotlinx.android.synthetic.main.fragement_detail_movie.textRunTime
import kotlinx.android.synthetic.main.fragement_detail_movie.textTitleMovie

class DetailMovieFragment : Fragment(), OnItemRecyclerViewClickListener<Video>, OnClickListener {
    private lateinit var movie: Movie
    private lateinit var viewModel: DiscoverViewMode
    private val videoAdapter: VideoAdapter by lazy { VideoAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragement_detail_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        registerLiveData()
    }

    override fun onClick(v: View?) {
        if (v == imageFavorite) {
            viewModel.addFavorite(movie)
        }
    }

    override fun onItemClick(data: Video) {
        startActivity(YoutubePlayerView.newInstance(context!!, data))
    }

    private fun initView() {
        recyclerViewTrailers.adapter = videoAdapter
        videoAdapter.setOnItemClickListener(this)
        imageFavorite.setOnClickListener(this)
    }

    private fun initData() {
        val movieDatabase = MovieDatabase.getInstance(context!!)
        val movieRepository = MovieRepository.instance(
            MovieLocalDataSource.getInstance(movieDatabase.movieDAO()), MovieRemoteDataSource.getInstance()
        )
        movie = arguments!!.getParcelable(EXTRA_MOVIE)
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(movieRepository))
            .get(DiscoverViewMode::class.java)
        viewModel.initMovieDetail(movie.id)
    }

    private fun registerLiveData() {
        viewModel.getMovieDetail().observe(this, Observer {
            if (it != null) {
                imageBackdropPath.loadImageUrl(QUALITY_IMAGE_500 + it.backdropPath)
                imagePosterPath.loadImageUrl(QUALITY_IMAGE_342 + it.posterPath)
                textTitleMovie.text = it.title
                ratingBarDetail.rating = it.voteAverageSeparate()
                textReleaseDate.text = it.releaseDate
                textRunTime.text = FragmentHelper.convertTime(it.runtime)
            }
        })
        viewModel.getMovieVideo().observe(this, Observer {
            if (it != null) {
                videoAdapter.addItems(it.videos!!)
            }
        })
        viewModel.isMovieFavorite().observe(this, Observer {
            if (it == true) {
                imageFavorite.setImageResource(R.drawable.ic_hearth)
            } else {
                imageFavorite.setImageResource(R.drawable.ic_unhearth)
            }
        })
    }

    companion object {
        private const val EXTRA_MOVIE = "EXTRA_MOVIE"
        private const val QUALITY_IMAGE_500 = "https://image.tmdb.org/t/p/w500"
        private const val QUALITY_IMAGE_342 = "https://image.tmdb.org/t/p/w342"

        fun instance(movie: Movie) = DetailMovieFragment().apply {
            val args = Bundle()
            args.putParcelable(EXTRA_MOVIE, movie)
            arguments = args
        }
    }
}
