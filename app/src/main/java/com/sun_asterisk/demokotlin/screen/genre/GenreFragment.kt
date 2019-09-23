package com.sun_asterisk.demokotlin.screen.genre

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Genre
import com.sun_asterisk.demokotlin.data.source.MovieRepository
import com.sun_asterisk.demokotlin.data.source.local.MovieDatabase
import com.sun_asterisk.demokotlin.data.source.local.MovieLocalDataSource
import com.sun_asterisk.demokotlin.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.demokotlin.screen.discover.DiscoverViewMode
import com.sun_asterisk.demokotlin.screen.genre.adapter.GenreAdapter
import com.sun_asterisk.demokotlin.screen.moviebygenre.MovieByGenreFragment
import com.sun_asterisk.demokotlin.utils.MyViewModelFactory
import com.sun_asterisk.demokotlin.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.demokotlin.utils.addFragmentToFragment
import kotlinx.android.synthetic.main.fragment_genre.recyclerViewGenre

class GenreFragment : Fragment(), OnItemRecyclerViewClickListener<Genre> {
    private lateinit var movieRepository: MovieRepository
    private lateinit var viewModel: DiscoverViewMode
    private lateinit var genreAdapter: GenreAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_genre, container, false)
        initView()
        initData()
        return view
    }

    override fun onItemClick(data: Genre) {
        addFragmentToFragment(R.id.layoutContainer,MovieByGenreFragment.instance(data),true)
    }

    private fun initView() {
        genreAdapter = GenreAdapter()
        genreAdapter.setOnItemClickListener(this)
        val movieDatabase = MovieDatabase.getInstance(context!!.applicationContext)
        val local: MovieLocalDataSource = MovieLocalDataSource.getInstance(movieDatabase.movieDAO())
        val remote: MovieRemoteDataSource = MovieRemoteDataSource.getInstance()
        movieRepository = MovieRepository.instance(local, remote)
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(movieRepository))
            .get(DiscoverViewMode::class.java)
        viewModel.initGenre()
        viewModel.getMovieGenre().observe(this, Observer {
            if (it != null) {
                genreAdapter.replaceItem(it.genres!!)
                recyclerViewGenre.adapter = genreAdapter
            }
        })
    }

    companion object {
        fun instance() = GenreFragment()
    }
}