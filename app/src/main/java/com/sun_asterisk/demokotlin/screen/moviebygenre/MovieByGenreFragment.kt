package com.sun_asterisk.demokotlin.screen.moviebygenre

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Genre
import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.source.MovieRepository
import com.sun_asterisk.demokotlin.data.source.local.MovieDatabase
import com.sun_asterisk.demokotlin.data.source.local.MovieLocalDataSource
import com.sun_asterisk.demokotlin.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.demokotlin.screen.detailmovie.DetailMovieFragment
import com.sun_asterisk.demokotlin.screen.discover.DiscoverViewMode
import com.sun_asterisk.demokotlin.screen.moviebygenre.adapter.MovieByGenreAdapter
import com.sun_asterisk.demokotlin.utils.EndlessScrollListener
import com.sun_asterisk.demokotlin.utils.MyViewModelFactory
import com.sun_asterisk.demokotlin.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.demokotlin.utils.addFragmentToFragment
import com.sun_asterisk.demokotlin.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_movie_by_genre.imageViewBack
import kotlinx.android.synthetic.main.fragment_movie_by_genre.recyclerViewMovieByGenre

class MovieByGenreFragment : Fragment(), OnItemRecyclerViewClickListener<Movie>, View.OnClickListener {
    private lateinit var viewModel: DiscoverViewMode
    private lateinit var genre: Genre
    private val movieByGenreAdapter: MovieByGenreAdapter by lazy { MovieByGenreAdapter() }
    private var page: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_by_genre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        registerLiveData()
    }

    override fun onClick(v: View?) {
        if (v == imageViewBack) {
            removeFragment(MovieByGenreFragment::class.java.simpleName)
        }
    }

    override fun onItemClick(data: Movie) {
        addFragmentToFragment(R.id.layoutContainer, DetailMovieFragment.instance(data), true)
    }

    private fun initView() {
        recyclerViewMovieByGenre.adapter = movieByGenreAdapter
        movieByGenreAdapter.setOnItemClickListener(this)
        imageViewBack.setOnClickListener(this)
    }

    private fun initData() {
        val movieDatabase = MovieDatabase.getInstance(context!!)
        val movieRepository = MovieRepository.instance(
            MovieLocalDataSource.getInstance(movieDatabase.movieDAO()),
            MovieRemoteDataSource.getInstance()
        )
        genre = arguments!!.getParcelable(EXTRA_GENRE)
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(movieRepository))
            .get(DiscoverViewMode::class.java)
        viewModel.initMovieByGenre(genre.id, page)
        recyclerViewMovieByGenre.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                page++
                viewModel.initMovieByGenre(genre.id, page)
            }
        })
    }

    private fun registerLiveData() {
        viewModel.getMovieByGenre().observe(this, Observer {
            if (it != null) {
                movieByGenreAdapter.addItems(it.movies!!)
            }
        })
    }

    companion object {
        private const val EXTRA_GENRE = "EXTRA_GENRE"
        fun instance(genre: Genre) = MovieByGenreFragment().apply {
            val args = Bundle()
            args.putParcelable(EXTRA_GENRE, genre)
            arguments = args
        }
    }
}
