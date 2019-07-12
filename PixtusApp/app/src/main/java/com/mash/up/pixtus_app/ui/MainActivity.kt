package com.mash.up.pixtus_app.ui

import android.graphics.Color
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

        initUI()

        NetworkCore.getNetworkCore<PixtusApi>()
            .getMain()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setData(it)
                //Log.d("main_data", it.toString())
            }, {
                it.printStackTrace()
            })
    }


    fun initUI(){
        Glide.with(this).asGif().load(R.raw.pixel_best).into(iv_gif)
        var dateFormat = SimpleDateFormat("MM.dd / EEE")
        tv_date.text = dateFormat.format(Date()).toString()
        btn_more.setOnClickListener(this)
        tv_exercise1.setTextColor(Color.parseColor("#ffffff"))
        tv_exercise2.setTextColor(Color.parseColor("#ffffff"))
        tv_exercise3.setTextColor(Color.parseColor("#ffffff"))
        tv_kcal1.setTextColor(Color.parseColor("#ffffff"))
        tv_kcal2.setTextColor(Color.parseColor("#ffffff"))
        tv_kcal3.setTextColor(Color.parseColor("#ffffff"))
        main_view1.setBackgroundColor(Color.parseColor("#ffffff"))
        main_view2.setBackgroundColor(Color.parseColor("#ffffff"))
        main_view3.setBackgroundColor(Color.parseColor("#ffffff"))
    }

    fun setData(model: Main){
        tv_title.text = model.characterName
        tv_calorie.text = model.exp.toString() + "exp"
        tv_total_calorie.text = model.nextExp.toString() + "exp"
        if(model.workouts.size >= 1){
            val sortedList = model.workouts.sortedWith(compareByDescending { it.totalKcal })

            tv_exercise1.setTextColor(Color.parseColor("#000000"))
            tv_kcal1.setTextColor(Color.parseColor("#5dbb96"))
            tv_exercise1.text = sortedList[0].exerciseName
            tv_kcal1.text = sortedList[0].totalKcal.toString() + "kcal"
            main_view1.setBackgroundColor(Color.parseColor("#d9d9d9"))
            if(model.workouts.size >= 2){
                tv_exercise2.setTextColor(Color.parseColor("#000000"))
                tv_kcal2.setTextColor(Color.parseColor("#5dbb96"))
                tv_exercise2.text = sortedList[1].exerciseName
                tv_kcal2.text = sortedList[1].totalKcal.toString() + "kcal"
                main_view2.setBackgroundColor(Color.parseColor("#d9d9d9"))
                if(model.workouts.size >= 3){
                    tv_exercise3.setTextColor(Color.parseColor("#000000"))
                    tv_kcal3.setTextColor(Color.parseColor("#5dbb96"))
                    tv_exercise3.text = sortedList[2].exerciseName
                    tv_kcal3.text = sortedList[2].totalKcal.toString() + "kcal"
                    main_view3.setBackgroundColor(Color.parseColor("#d9d9d9"))
                }
            }
        }
    }
}