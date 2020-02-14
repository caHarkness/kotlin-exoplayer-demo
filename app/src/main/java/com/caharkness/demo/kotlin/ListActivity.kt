package com.caharkness.demo.kotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.GradientDrawable
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
import androidx.core.widget.NestedScrollView
import com.caharkness.demo.kotlin.models.VideoModel
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylist
import com.google.android.exoplayer2.util.Util
import org.json.JSONArray
import org.json.JSONObject


class ListActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //
        //  Normally, we would pass bundle data via intents, but this activity is launched from the App Launcher
        //  So for this example, we are instantiating a bundle.
        //
        var bundle = Bundle()

        //
        //  Let's pretend we launched this activity with a blob of JSON
        //  Received from a GET or POST request... (or a local file?)
        //
        val j = """
            [
                {
                    title: "Video 1",
                    description: "Description of video 1",
                    address: "https://s3.amazonaws.com/interview-quiz-stuff/tos-trailer/master.m3u8"
                },
                
                {
                    title: "Video 2",
                    description: "Description of video 2",
                    address: "https://s3.amazonaws.com/interview-quiz-stuff/tos/master.m3u8"
                },
                
                {
                    title: "Video 1 & 2",
                    description: "Play video 1 & 2 (concatenating media source)",
                    address: "https://s3.amazonaws.com/interview-quiz-stuff/tos-trailer/master.m3u8",
                    playnext: "https://s3.amazonaws.com/interview-quiz-stuff/tos/master.m3u8"
                },
                
                {
                    title: "Video 2 & 1",
                    description: "Play video 2 & 1 (in reverse order)",
                    address: "https://s3.amazonaws.com/interview-quiz-stuff/tos/master.m3u8",
                    playnext: "https://s3.amazonaws.com/interview-quiz-stuff/tos-trailer/master.m3u8"
                }
            ]
        """.trimIndent()
        bundle.putString("json", j);

        //
        //  At this point in the code, we are breaking down the JSON
        //  To show a list of clickable videos.
        //
        val a = JSONArray(bundle.getString("json"))
        val l = LinearLayout(this)

        for (i in 0 until a.length())
        {
            val o: JSONObject = a.getJSONObject(i)
            val vm = VideoModel(o)

            //
            //
            //
            l.addView(vm.getListItemView(this))
        }

        l.orientation = LinearLayout.VERTICAL
        l.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f)

        val
            toolbar = Toolbar(this)
            toolbar.setBackgroundColor(0xFFFF0000.toInt())
            toolbar.setTitleTextColor(0xFFFFFFFF.toInt())
            toolbar.setTitle("Videos")

        val
            nsv = NestedScrollView(this)
            nsv.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f)
            nsv.isFillViewport = true
            nsv.addView(l)

        val
            ml = LinearLayout(this)
            ml.orientation = LinearLayout.VERTICAL
            ml.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f)

            ml.addView(toolbar)
            ml.addView(nsv)

        setContentView(ml)
    }
}