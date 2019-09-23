package com.sun_asterisk.demokotlin.screen.favorite

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.source.MovieRepository
import com.sun_asterisk.demokotlin.data.source.local.MovieDatabase
import com.sun_asterisk.demokotlin.data.source.local.MovieLocalDataSource
import com.sun_asterisk.demokotlin.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.demokotlin.screen.discover.DiscoverViewMode
import com.sun_asterisk.demokotlin.screen.favorite.adapter.MainListAdapter
import com.sun_asterisk.demokotlin.utils.MyViewModelFactory
import com.sun_asterisk.demokotlin.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_favotite.view.recyclerViewFavorite

class FavoriteFragment : Fragment(), OnItemRecyclerViewClickListener<Movie> {
    private lateinit var movieRepository: MovieRepository
    private lateinit var viewModel: DiscoverViewMode
    private lateinit var mainAdapter: MainListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favotite, container, false)
        initView()
        initData(view)
        return view
    }

    override fun onItemClick(data: Movie) {
        viewModel.removeMovieLocal(data)
    }

    private fun initView() {
        mainAdapter = MainListAdapter()
        mainAdapter.setOnItemClickListener(this)
        val movieDatabase = MovieDatabase.getInstance(context!!.applicationContext)
        val local: MovieLocalDataSource = MovieLocalDataSource.getInstance(movieDatabase.movieDAO())
        val remote: MovieRemoteDataSource = MovieRemoteDataSource.getInstance()
        movieRepository = MovieRepository.instance(local, remote)
    }

    private fun initData(view: View) {
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(movieRepository))
            .get(DiscoverViewMode::class.java)
        viewModel.initMovieLocal()
        viewModel.getMovieLocal().observe(this, Observer {
            if (it != null) {
                mainAdapter.replaceItem(it)
                view.recyclerViewFavorite.adapter = mainAdapter
            }
        })
    }

    companion object {
        fun instance() = FavoriteFragment()
    }
}