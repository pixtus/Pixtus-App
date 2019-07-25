package com.mash.up.pixtus_app.ui

import android.os.Bundle
import com.bumptech.glide.Glide
import android.view.View
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    fun initUI(){
        Glide.with(this).asGif().load(R.raw.pixel_best).into(iv_gif)

        var dateFormat = SimpleDateFormat("MM.dd / EEE")
        tv_date.text = dateFormat.format(Date()).toString()
    }
}