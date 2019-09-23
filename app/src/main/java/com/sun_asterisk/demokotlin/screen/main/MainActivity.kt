package com.sun_asterisk.demokotlin.screen.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.screen.home.HomeFragment
import com.sun_asterisk.myeditor03.utils.addFragmentToActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragmentToActivity(R.id.layoutContainer, HomeFragment.instance(), true)
    }
}
