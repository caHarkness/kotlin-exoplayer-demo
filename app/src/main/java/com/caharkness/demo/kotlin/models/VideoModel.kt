package com.caharkness.demo.kotlin.models

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.caharkness.demo.kotlin.DemoApplication
import com.caharkness.demo.kotlin.VideoModelActivity
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import org.json.JSONObject

class VideoModel
{
    //
    //  The actual VideoModel "model"
    //
    var o: JSONObject? = null
    var title: String?
    var desc: String?
    var address: String?
    var playnext: String?

    constructor(o: JSONObject)
    {
        this.o = o
        title = o.getString("title")
        desc = o.getString("description")
        address = o.getString("address")

        if (o.has("playnext"))
            playnext = o.getString("playnext")
        else
            playnext = null
    }

    //
    //  The functional part of the VideoModel model
    //
    override fun toString(): String
    {
        return o.toString()
    }

    fun getHlsMediaSource(c: Context): MediaSource
    {
        val dataSourceFactory =
            DefaultDataSourceFactory(
                c,
                Util.getUserAgent(c, "Kotlin Demo"))

        val vid =
            HlsMediaSource
                .Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(address))

        if (playnext != null)
        {
            return ConcatenatingMediaSource(
                vid,
                HlsMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(playnext)))
        }

        return vid
    }

    //
    //  Get the clickable "ListView" representation of this VideoModel
    //  Note: We're not actually using a ListView, just a LinearLayout of LinearLayouts
    //
    fun getListItemView(c: Context): LinearLayout
    {
        val layout = LinearLayout(c)
        var tcolor: Int

        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        layout.setPadding(DemoApplication.inches(1/8f))

        layout.addView(
            object : TextView(c)
            {
                init
                {
                    text = title
                    textSize = 18f
                    tcolor = currentTextColor
                }
            })

        layout.addView(
            object : TextView(c)
            {
                init
                {
                    text = desc
                    textSize = 12f
                    setTextColor(currentTextColor - 0x40000000.toInt())
                }
            })

        val cont = LinearLayout(c)

        cont.orientation = LinearLayout.VERTICAL
        cont.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        cont.addView(layout)
        cont.addView(
            object : LinearLayout(c)
            {
                init
                {
                    layoutParams =
                        LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)

                    minimumHeight = 1
                    setBackgroundColor(tcolor)
                }
            })

        layout.isClickable = true
        layout.setOnClickListener {
            var intent = Intent(c, VideoModelActivity::class.java)
            intent.putExtra("json", toString())
            c.startActivity(intent)
        }

        return cont
    }

    fun getVideoView(c: Context): PlayerView
    {
        val exo = SimpleExoPlayer.Builder(c).build()
        val view = PlayerView(c)

        view.layoutParams =
        LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            1f)

        view.player = exo
        view.addOnAttachStateChangeListener(
            object: View.OnAttachStateChangeListener
            {
                override fun onViewAttachedToWindow(p0: View?)
                {
                    exo.prepare(getHlsMediaSource(c))
                    exo.playWhenReady = true
                }

                override fun onViewDetachedFromWindow(p0: View?)
                {
                    exo.stop()
                    exo.release()
                }
            })

        return view;
    }
}