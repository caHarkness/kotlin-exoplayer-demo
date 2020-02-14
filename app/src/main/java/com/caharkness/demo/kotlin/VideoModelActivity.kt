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
import com.caharkness.demo.kotlin.models.VideoModel
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylist
import com.google.android.exoplayer2.util.Util
import org.json.JSONObject


class VideoModelActivity : AppCompatActivity()
{
    lateinit var model: VideoModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val
            o = JSONObject(intent.extras.getString("json"))
            model = VideoModel(o)

        //
        //  Create the activity's view using the model
        //  (Toolbar + ExoPlayer)
        //
        val
            toolbar = Toolbar(this)
            toolbar.setBackgroundColor(0xFFFF0000.toInt())
            toolbar.setTitleTextColor(0xFFFFFFFF.toInt())
            toolbar.setTitle(model.title)
            toolbar.setSubtitle(model.desc)

        val
            layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            layout.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)

            layout.addView(toolbar)
            layout.addView(model.getVideoView(this))

        setContentView(layout)
    }
}