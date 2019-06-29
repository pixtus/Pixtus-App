package com.mash.up.pixtus_app.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.gson.annotations.SerializedName
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.fab1
import kotlinx.android.synthetic.main.activity_main.fab2

class MainActivity : BaseActivity(), View.OnClickListener {
    private var isFabOpen : Boolean = false
    var fab_open : Animation?= null
    var fab_close : Animation?= null

    override fun onClick(v: View?) {
        var id = v?.id
        when(id){
            R.id.btn_more ->{
                anim()
                Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show()
            }
            R.id.fab1 -> {
                anim()
                Toast.makeText(this, "Button1", Toast.LENGTH_SHORT).show()
            }
            R.id.fab2 -> {
                anim()
                Toast.makeText(this, "Button2", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)

        btn_more.setOnClickListener(this)
        fab1.setOnClickListener(this)
        fab2.setOnClickListener(this)
    }

    fun anim(){
        if(isFabOpen) buttonOpen()
        else buttonClose()
    }

    private fun buttonOpen(){
        fab_close.let {
            fab1.startAnimation(it)
            fab2.startAnimation(it)
        }
        fab1.startAnimation(fab_close)
        fab2.startAnimation(fab_close)
        fab1.isClickable = false
        fab2.isClickable = false
        isFabOpen = false
    }
    private fun buttonClose(){
        fab1.startAnimation(fab_open)
        fab2.startAnimation(fab_open)
        fab1.isClickable = true
        fab2.isClickable = true
        isFabOpen = true
    }
}