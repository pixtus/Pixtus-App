package com.mash.up.pixtus_app.ui.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class  UnScrollViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }

}