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
import com.caharkness.demo.kotlin.adapters.VideoModelAdapter
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
                    id: 0,
                    title: "Video 1",
                    description: "Description of video 1",
                    address: "https://s3.amazonaws.com/interview-quiz-stuff/tos-trailer/master.m3u8"
                },
                
                {
                    id: 1,
                    title: "Video 2",
                    description: "Description of video 2",
                    address: "https://s3.amazonaws.com/interview-quiz-stuff/tos/master.m3u8"
                },
                
                {
                    id: 2,
                    title: "Video 1 & 2",
                    description: "Play video 1 & 2 (concatenating media source)",
                    address: "https://s3.amazonaws.com/interview-quiz-stuff/tos-trailer/master.m3u8",
                    playnext: "https://s3.amazonaws.com/interview-quiz-stuff/tos/master.m3u8"
                },
                
                {
                    id: 3,
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

        val
            toolbar = Toolbar(this)
            toolbar.setBackgroundColor(0xFFFF0000.toInt())
            toolbar.setTitleTextColor(0xFFFFFFFF.toInt())
            toolbar.setTitle("Videos")
            toolbar.elevation = 15f

        val
            lv = ListView(this)
            lv.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f)
            lv.descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS
            lv.adapter = VideoModelAdapter(this, a)
            lv.setOnItemClickListener { adapterView, view, i, _ -> {
                /*

                Spending too much time figuring out why this isn't
                Firing when I tap on an item, see VideoModel's .watch() method instead...

                Toast.makeText(this, "Hello world " + i, Toast.LENGTH_LONG).show()

                (lv.adapter as VideoModelAdapter)
                    .getItemModel(i)
                    .watch(this)

                */
            }}

        val
            ml = LinearLayout(this)
            ml.orientation = LinearLayout.VERTICAL
            ml.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f)

            ml.addView(toolbar)
            ml.addView(lv)


        setContentView(ml)
    }
}