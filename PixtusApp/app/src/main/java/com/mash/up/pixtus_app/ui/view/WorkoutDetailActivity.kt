package com.mash.up.pixtus_app.ui.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_workout_detail.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener



class WorkoutDetailActivity : AppCompatActivity() {
    var timestop: Long = 0
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mash.up.pixtus_app.R.layout.activity_workout_detail)

        if (intent.hasExtra("workout_name")) {
            val str = intent.getStringExtra("workout_name")
            tool_workout_name.text = str
            when (str) {
                //gif파일 바꿔주기
                "축구" -> Glide.with(this).asGif().load(com.mash.up.pixtus_app.R.raw.pixel_best).into(iv_workout_detail)
                "자전거" -> Glide.with(this).asGif().load(com.mash.up.pixtus_app.R.raw.pixel_best).into(iv_workout_detail)
                "수영" -> Glide.with(this).asGif().load(com.mash.up.pixtus_app.R.raw.pixel_best).into(iv_workout_detail)
            }
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }

        btn_workout_back.setOnClickListener {
            finish()
        }
        initUI()

        btn_timer_start.setOnClickListener {
            if(!flag){

                timestop = chronometer.base - SystemClock.elapsedRealtime()
                chronometer.start()
                flag = true
                btn_timer_start.setImageResource(com.mash.up.pixtus_app.R.drawable.btn_stop)
            }else{
                timestop = chronometer.base - SystemClock.elapsedRealtime()
                chronometer.stop()
            }


        }
    }

    fun initUI() {
        Glide.with(this).asGif().load(com.mash.up.pixtus_app.R.raw.pixel_best).into(iv_workout_detail)
    }
}
