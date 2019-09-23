package com.sun_asterisk.demokotlin.screen.home

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.screen.discover.DiscoverFragment
import com.sun_asterisk.demokotlin.screen.favorite.FavoriteFragment
import com.sun_asterisk.demokotlin.screen.genre.GenreFragment
import com.sun_asterisk.demokotlin.utils.addFragmentToFragment
import kotlinx.android.synthetic.main.fragment_main.navigationMenuHome

class HomeFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addFragmentToFragment(R.id.fragmentLayoutContent, DiscoverFragment.instance(), true)
        navigationMenuHome.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.actionHome -> {
                addFragmentToFragment(R.id.fragmentLayoutContent, DiscoverFragment.instance(), true)
                true
            }
            R.id.actionGenre -> {
                addFragmentToFragment(R.id.fragmentLayoutContent, GenreFragment.instance(), true)
                true
            }
            R.id.actionFavorite -> {
                addFragmentToFragment(R.id.fragmentLayoutContent, FavoriteFragment.instance(), true)
                true
            }
            else -> false
        }
    }

    companion object {
        fun instance() = HomeFragment()
    }
}