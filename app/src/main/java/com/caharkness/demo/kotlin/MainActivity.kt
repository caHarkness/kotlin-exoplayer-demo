package com.caharkness.demo.kotlin

import android.content.Context
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util.getUserAgent
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylist
import com.google.android.exoplayer2.util.Util


class MainActivity : AppCompatActivity()
{
    lateinit var layout: LinearLayout
    lateinit var toolbar: Toolbar
    lateinit var exoPlayer: SimpleExoPlayer
    lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //
        //  SimpleExoPlayer
        //
        exoPlayer = SimpleExoPlayer.Builder(this).build()

        playerView = PlayerView(this)
        playerView.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f)

        playerView.player = exoPlayer

        //
        //  Toolbar
        //
        toolbar = Toolbar(this)
        toolbar.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)

        toolbar.title = "Demo Video Player"
        toolbar.setBackgroundColor(0xFFFF0000.toInt())

        toolbar
            .menu
            .add("Video 1")
            .setOnMenuItemClickListener(
            {
                toolbar.title = "Video 1"

                val dataSourceFactory =
                    DefaultDataSourceFactory(
                        this,
                        Util.getUserAgent(this, "Kotlin Demo"))

                val vid1 =
                    HlsMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(
                            Uri.parse("https://s3.amazonaws.com/interview-quiz-stuff/tos-trailer/master.m3u8"))

                exoPlayer.prepare(vid1)
                exoPlayer.playWhenReady = true
                true
            })

        toolbar
            .menu
            .add("Video 2")
            .setOnMenuItemClickListener(
            {
                toolbar.title = "Video 2"

                val dataSourceFactory =
                    DefaultDataSourceFactory(
                        this,
                        Util.getUserAgent(this, "Kotlin Demo"))

                val vid1 =
                    HlsMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(
                            Uri.parse("https://s3.amazonaws.com/interview-quiz-stuff/tos/master.m3u8"))

                exoPlayer.prepare(vid1)
                exoPlayer.playWhenReady = true
                true
            })

        toolbar
            .menu
            .add("Play Both")
            .setOnMenuItemClickListener(
            {
                toolbar.title = "Video 1 & 2"

                val dataSourceFactory =
                    DefaultDataSourceFactory(
                        this,
                        Util.getUserAgent(this, "Kotlin Demo"))

                val vid1 =
                    HlsMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(
                            Uri.parse("https://s3.amazonaws.com/interview-quiz-stuff/tos-trailer/master.m3u8"))

                val vid2 =
                    HlsMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(
                            Uri.parse("https://s3.amazonaws.com/interview-quiz-stuff/tos/master.m3u8"))

                //
                //  Combine the two media sources (playlists) from the links above.
                //
                val vid3 = ConcatenatingMediaSource(vid1, vid2)


                exoPlayer.prepare(vid3)
                exoPlayer.playWhenReady = true

                true
            })

        //
        //  Building the layout
        //

        layout = LinearLayout(this)

        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams =
            object : LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        {
            init
            {
                gravity = Gravity.CENTER
            }
        }

        layout.setBackgroundColor(0xFF000000.toInt())
        layout.addView(toolbar)
        layout.addView(playerView)

        setContentView(layout)
    }
}