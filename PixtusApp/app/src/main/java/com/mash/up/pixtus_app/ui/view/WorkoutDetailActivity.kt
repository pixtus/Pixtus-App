package com.mash.up.pixtus_app.ui.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_workout_detail.*
import android.support.v4.os.HandlerCompat.postDelayed
import android.util.Log
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class WorkoutDetailActivity : AppCompatActivity() {
    var flag = 0
    var handler: Handler? = null
    var MillisecondTime: Long = 0
    var StartTime: Long = 0
    var TimeBuff: Long = 0
    var UpdateTime: Long = 0
    var StopTime: Long = 0
    var Seconds: Long = 0
    var Minutes: Long = 0
    var MilliSeconds: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mash.up.pixtus_app.R.layout.activity_workout_detail)

        initUI()
        var str = intent.getStringExtra("workout_id")
        tool_workout_name.text = intent.getStringExtra("workout_name")
        when (intent.getStringExtra("workout_name")) {
            //gif파일 바꿔주기
            "수영" -> Glide.with(this).asGif().load(com.mash.up.pixtus_app.R.raw.pixtus_swim).into(iv_workout_detail)//걷기
            "축구" -> Glide.with(this).asGif().load(com.mash.up.pixtus_app.R.raw.pixtus_ball_).into(iv_workout_detail)//축구
            "자전거" -> Glide.with(this).asGif().load(com.mash.up.pixtus_app.R.raw.pixtus_bike_250px).into(iv_workout_detail)//자전거
        }

        btn_workout_back.setOnClickListener {
            finish()
        }
        handler = Handler()
        btn_timer_start.setOnClickListener {
            if (flag == 0) {
                StartTime = SystemClock.uptimeMillis()
                handler?.postDelayed(runnable, 0)
                flag = 1
                btn_timer_start.setImageResource(com.mash.up.pixtus_app.R.drawable.btn_stop)
            } else if (flag == 1) {
                TimeBuff += MillisecondTime
                handler?.removeCallbacks(runnable)
                StopTime = SystemClock.uptimeMillis()
                flag = 2
                btn_timer_start.setImageResource(com.mash.up.pixtus_app.R.drawable.btn_check)
            } else if (flag == 2) {
                StopTime = StopTime - StartTime
                Log.d("하하", StopTime.toString())

                //서버에 시간 요청 보내기
                var params: HashMap<String, Any> = HashMap<String, Any>()
                params.put("exerciseId", str)
                params.put("time", StopTime/1000)
                params.put("uid", "abcd123456")//일단..

                NetworkCore.getNetworkCore<PixtusApi>()
                    .sendWork(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                    }, {
                        it.printStackTrace()
                    })
            }
        }
    }

    var runnable: Runnable = object : Runnable {
        override fun run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime
            UpdateTime = TimeBuff + MillisecondTime
            Seconds = (UpdateTime / 1000) //as Int
            Minutes = Seconds / 60
            Seconds = Seconds % 60
            MilliSeconds = (UpdateTime % 1000) //as Int
            chronometer.setText(
                "" + Minutes + ":"
                        + String.format("%02d", Seconds) + ":"
                        + String.format("%03d", MilliSeconds)
            )
            handler!!.postDelayed(this, 0)
        }

    }

    fun initUI() {
        Glide.with(this).asGif().load(com.mash.up.pixtus_app.R.raw.pixel_best).into(iv_workout_detail)
    }
}
