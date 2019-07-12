package com.mash.up.pixtus_app.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.Toolbar
import android.util.Log
import com.bumptech.glide.Glide
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.gson.annotations.SerializedName
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.core.Main
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
                com.mash.up.pixtus_app.ui.view.Dialog.BottomSheetDialog().show(supportFragmentManager,"")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkCore.getNetworkCore<PixtusApi>()
            .getMain()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //TODO it.characterName 이렇게 참조해주시면 됩니당
                //TODO 보통 fun setData(model : Main) 이런함수(데이터를 받아서 뷰에다가 뿌려주는 함수)를 만들어서 깔끔하게 네트워크 통신 로직부븐을 정리 할 수있어욤
                Log.d("main_data", it.toString())
            }, {
                it.printStackTrace()
            })

        initUI()


    }


    fun initUI(){
        Glide.with(this).asGif().load(R.raw.pixel_best).into(iv_gif)

        var dateFormat = SimpleDateFormat("MM.dd / EEE")
        tv_date.text = dateFormat.format(Date()).toString()
        btn_more.setOnClickListener(this)
    }
}