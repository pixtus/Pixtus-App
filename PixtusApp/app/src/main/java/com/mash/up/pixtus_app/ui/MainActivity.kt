package com.mash.up.pixtus_app.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.Toolbar
import com.bumptech.glide.Glide
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.gson.annotations.SerializedName
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity(), View.OnClickListener {
    private var isFabOpen : Boolean = false

    override fun onClick(v: View?) {
        var id = v?.id
        when(id){
            R.id.btn_more ->{
                anim()
                com.mash.up.pixtus_app.ui.view.Dialog.BottomSheetDialog().show(supportFragmentManager,"")

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

    }


    fun initUI(){
        Glide.with(this).asGif().load(R.raw.pixel_best).into(iv_gif)

        var dateFormat = SimpleDateFormat("MM.dd / EEE")
        tv_date.text = dateFormat.format(Date()).toString()

        btn_more.setOnClickListener(this)
    }

    fun anim(){
        if(isFabOpen) buttonOpen()
        else buttonClose()
    }

    private fun buttonOpen(){

        isFabOpen = false
    }
    private fun buttonClose(){

        isFabOpen = true
    }
}