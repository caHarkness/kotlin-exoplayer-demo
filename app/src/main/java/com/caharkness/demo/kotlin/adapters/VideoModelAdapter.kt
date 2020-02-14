package com.caharkness.demo.kotlin.adapters

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.caharkness.demo.kotlin.models.VideoModel
import org.json.JSONArray
import org.json.JSONObject

class VideoModelAdapter(c: Context, a: JSONArray) : BaseAdapter()
{
    private var context: Context = c
    private var array: JSONArray = a

    override fun getView(i: Int, v: View?, parent: ViewGroup?): View
    {
        return getItemModel(i).getListItemView(context)
    }

    override fun getItem(i: Int): JSONObject
    {
        return array.getJSONObject(i)
    }

    fun getItemModel(i: Int): VideoModel
    {
        return VideoModel(getItem(i))
    }

    override fun getItemId(i: Int): Long
    {
        return getItem(i).getLong("id")
    }

    override fun getCount(): Int
    {
        return array.length()
    }


}