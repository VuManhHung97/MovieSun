package com.sun_asterisk.demokotlin.screen.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Movie
import com.sun_asterisk.demokotlin.data.source.MovieRepository
import com.sun_asterisk.demokotlin.data.source.local.MovieDatabase
import com.sun_asterisk.demokotlin.data.source.local.MovieLocalDataSource
import com.sun_asterisk.demokotlin.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.demokotlin.screen.discover.DiscoverViewMode
import com.sun_asterisk.demokotlin.screen.favorite.adapter.MainListAdapter
import com.sun_asterisk.demokotlin.utils.FragmentHelper.Companion.hideKeyboard
import com.sun_asterisk.demokotlin.utils.MyViewModelFactory
import com.sun_asterisk.demokotlin.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.activity_search.editTextSearch
import kotlinx.android.synthetic.main.activity_search.recyclerViewFavorite

class SearchActivity : AppCompatActivity(), OnItemRecyclerViewClickListener<Movie> {
    override fun onItemClick(data: Movie) {

    }

    private lateinit var movieRepository: MovieRepository
    private lateinit var viewModel: DiscoverViewMode
    private lateinit var mainAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar!!.hide()
        initView()
        initData()
        addEvent()
    }

    private fun initView() {
        mainAdapter = MainListAdapter()
        mainAdapter.setOnItemClickListener(this)
        val movieDatabase = MovieDatabase.getInstance(applicationContext)
        val local: MovieLocalDataSource = MovieLocalDataSource.getInstance(movieDatabase.movieDAO())
        val remote: MovieRemoteDataSource = MovieRemoteDataSource.getInstance()
        movieRepository = MovieRepository.instance(local, remote)
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(movieRepository))
            .get(DiscoverViewMode::class.java)
    }

    private fun addEvent() {
        editTextSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(textView: TextView?, id: Int, keyEvent: KeyEvent?): Boolean {
                if (id == EditorInfo.IME_ACTION_DONE && editTextSearch.text.toString().trim().isNotEmpty()) {
                    hideKeyboard(this@SearchActivity)
                    viewModel.initMovieSearch(editTextSearch.text.toString(), 1)
                    viewModel.getMovieSearch().observe(this@SearchActivity, Observer {
                        if (it != null) {
                            mainAdapter.replaceItem(it)
                            recyclerViewFavorite.adapter = mainAdapter
                        }
                    })
                    return true
                }
                return false
            }
        })
    }

    companion object {
        fun newInstance(context: Context): Intent = Intent(
            context,
            SearchActivity::class.java
        )
    }
}