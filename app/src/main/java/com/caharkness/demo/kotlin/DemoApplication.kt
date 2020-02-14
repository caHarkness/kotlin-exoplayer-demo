package com.caharkness.demo.kotlin

import android.app.Application
import android.util.TypedValue

class DemoApplication : Application()
{
    companion object
    {
        lateinit var self: DemoApplication

        //
        //  Useful function for getting pixel values from "real life" measurements
        //  In this case: inches
        //
        fun inches(i: Float): Int
        {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_IN,
                i,
                self.getResources()
                    .getDisplayMetrics()
            ).toInt()
        }
    }

    override fun onCreate()
    {
        super.onCreate()
        DemoApplication.self = this
    }
}