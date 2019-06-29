package com.mash.up.pixtus_app.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.google.gson.annotations.SerializedName
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar(toolbar as Toolbar)

        iv_main.setBackgroundResource(R.drawable.image_run)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        (iv_main.background as AnimationDrawable)?.start()
    }
}