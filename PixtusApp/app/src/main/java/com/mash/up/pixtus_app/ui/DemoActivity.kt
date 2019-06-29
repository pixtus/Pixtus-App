package com.mash.up.pixtus_app.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import com.mash.up.pixtus_app.R
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity(), View.OnClickListener{
    private var isFabOpen : Boolean = false
    var fab_open : Animation ?= null
    var fab_close : Animation?= null

    override fun onClick(v: View?) {
        var id = v?.id
        when(id){
            R.id.fab ->{
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
        setContentView(R.layout.activity_demo)

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)


        fab.setOnClickListener(this)
        fab1.setOnClickListener(this)
        fab2.setOnClickListener(this)

    }

    fun anim(){
        if(!isFabOpen) buttonOpen()
        if(isFabOpen) buttonClose()
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