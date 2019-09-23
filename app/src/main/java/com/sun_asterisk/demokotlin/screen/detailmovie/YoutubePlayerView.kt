package com.sun_asterisk.demokotlin.screen.detailmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.Provider
import com.sun_asterisk.demokotlin.BuildConfig
import com.sun_asterisk.demokotlin.R
import com.sun_asterisk.demokotlin.data.model.Video
import kotlinx.android.synthetic.main.activity_youtube_player.youtubePlayerView

class YoutubePlayerView : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private lateinit var video: Video
    override fun onInitializationSuccess(p0: Provider?, p1: YouTubePlayer?, b: Boolean) {
        p1!!.loadVideo(video.key)
        p1.setFullscreen(true)
    }

    override fun onInitializationFailure(p0: Provider?, p1: YouTubeInitializationResult?) {
        if (p1!!.isUserRecoverableError) {
            p1.getErrorDialog(this, 1232)
        } else {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1232) {
            youtubePlayerView.initialize(BuildConfig.YOUTUBE_API_KEY, this)
        }
    }

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        setContentView(R.layout.activity_youtube_player)
        youtubePlayerView.initialize(BuildConfig.YOUTUBE_API_KEY, this)
        video = intent.getParcelableExtra(EXTRA_VIDEO)
    }

    companion object {
        private const val EXTRA_VIDEO = "EXTRA_VIDEO"
        fun newInstance(context: Context, video: Video): Intent = Intent(
            context,
            YoutubePlayerView::class.java
        ).apply {
            putExtra(EXTRA_VIDEO, video)
        }
    }
}