package com.sun_asterisk.demokotlin.screen.discover

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.source.MovieRepository
import com.sun_asterisk.demokotlin.data.source.local.MovieDatabase
import com.sun_asterisk.demokotlin.data.source.local.MovieLocalDataSource
import com.sun_asterisk.demokotlin.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.demokotlin.screen.detailmovie.DetailMovieFragment
import com.sun_asterisk.demokotlin.screen.discover.adapter.BannerAdapter
import com.sun_asterisk.demokotlin.screen.discover.adapter.MovieAdapter
import com.sun_asterisk.demokotlin.screen.search.SearchActivity
import com.sun_asterisk.demokotlin.utils.EndlessScrollListener
import com.sun_asterisk.demokotlin.utils.MyViewModelFactory
import com.sun_asterisk.demokotlin.utils.OnItemClickListener
import com.sun_asterisk.demokotlin.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.demokotlin.utils.addFragmentToFragment
import kotlinx.android.synthetic.main.fragment_discover.imageViewSearch
import kotlinx.android.synthetic.main.fragment_discover.recyclerViewComingSoon
import kotlinx.android.synthetic.main.fragment_discover.recyclerViewNowPlaying
import kotlinx.android.synthetic.main.fragment_discover.recyclerViewPopular
import kotlinx.android.synthetic.main.fragment_discover.tabLayoutIndicatorDiscover
import kotlinx.android.synthetic.main.fragment_discover.viewPagerBannerDiscover

class DiscoverFragment : Fragment(), OnItemClickListener, OnItemRecyclerViewClickListener<Movie>,
    View.OnClickListener,
    ViewPager.OnPageChangeListener {
    private lateinit var viewModel: DiscoverViewMode
    private lateinit var movies: List<Movie>
    private val adapterBanner: BannerAdapter by lazy { BannerAdapter(context!!.applicationContext) }
    private val adapterMoviePopular: MovieAdapter by lazy { MovieAdapter() }
    private val adapterMovieComingSoon: MovieAdapter by lazy { MovieAdapter() }
    private val adapterMovieNowPlaying: MovieAdapter by lazy { MovieAdapter() }
    private var page: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        registerLiveData()
    }

    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(p0: Int) {
        adapterBanner.setCurrentPosition(p0)
    }

    override fun onClick(v: View?) {
        if (v == imageViewSearch) {
            startActivity(SearchActivity.newInstance(context!!.applicationContext))
        }
    }

    override fun onItemRecyclerViewClick(movie: Movie) {
        addFragmentToFragment(R.id.layoutContainer,DetailMovieFragment.instance(movie),true)
    }

    override fun onItemClick(data: Movie) {
        addFragmentToFragment(R.id.layoutContainer,DetailMovieFragment.instance(data),true)
    }

    private fun initView() {
        viewPagerBannerDiscover.adapter = adapterBanner
        tabLayoutIndicatorDiscover.setupWithViewPager(viewPagerBannerDiscover, true)
        viewPagerBannerDiscover.addOnPageChangeListener(this)
        adapterMovieNowPlaying.setOnItemClickListener(this)
        adapterBanner.setOnItemClickListener(this)
        adapterMoviePopular.setOnItemClickListener(this)
        adapterMovieComingSoon.setOnItemClickListener(this)
        imageViewSearch.setOnClickListener(this)
    }

    private fun initData() {
        val movieDatabase = MovieDatabase.getInstance(context!!.applicationContext)
        val movieRepository = MovieRepository.instance(
            MovieLocalDataSource.getInstance(movieDatabase.movieDAO()),
            MovieRemoteDataSource.getInstance()
        )
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(movieRepository))
            .get(DiscoverViewMode::class.java)
        viewModel.init(page)
        recyclerViewPopular.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                page++
                viewModel.init(page)
            }
        })
        recyclerViewComingSoon.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                page++
                viewModel.init(page)
            }
        })
        recyclerViewNowPlaying.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                page++
                viewModel.init(page)
            }
        })
    }

    private fun registerLiveData(){
        viewModel.movieResponseLive.observe(this, Observer { movieResponse ->
            if (movieResponse != null) {
                adapterBanner.updateData(movieResponse.movies!!.subList(0, 5))
                tabLayoutIndicatorDiscover.setupWithViewPager(viewPagerBannerDiscover, true)
                movies = movieResponse.movies!!
            }
        })
        viewModel.moviePopularLive.observe(this, Observer {
            if (it != null) {
                adapterMoviePopular.addItems(it.movies!!)
                recyclerViewPopular.adapter = adapterMoviePopular
            }
        })
        viewModel.movieComingSoonLive.observe(this, Observer {
            if (it != null) {
                adapterMovieComingSoon.addItems(it.movies!!)
                recyclerViewComingSoon.adapter = adapterMovieComingSoon
            }
        })
        viewModel.movieNowPlayingLive.observe(this, Observer  {
            if (it != null) {
                adapterMovieNowPlaying.addItems(it.movies!!)
                recyclerViewNowPlaying.adapter = adapterMovieNowPlaying
            }
        })
        viewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(view!!.context, it!!.message, Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        fun instance() = DiscoverFragment()
    }
}