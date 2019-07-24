package com.mash.up.pixtus_app.ui.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.constraint.ConstraintLayout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_workout_detail.*
import android.view.View
import android.widget.*
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class WorkoutDetailActivity : AppCompatActivity() {
    var handler: Handler? = null
    var hour: TextView? = null
    var minute: TextView? = null
    var seconds: TextView? = null

    internal var MillisecondTime: Long = 0
    internal var StartTime: Long = 0
    internal var LastTime: Long = 0
    internal var TimeBuff: Long = 0
    internal var UpdateTime = 0L

    internal var Seconds: Int = 0
    internal var Minutes: Int = 0

    internal var layoutFlag: Int = 1//state = 1 2 3
    internal var buttonFlag: Boolean = true
    var startButton: ImageButton? = null
    var buttonSet: LinearLayout? = null
    var pauseButton: ImageButton? = null
    var buttonDone : ImageButton? = null
    var buttonDoneAct : ImageButton? = null
    var showExp : ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_detail)
        btn_workout_back.setOnClickListener { finish() }
        startButton = findViewById(R.id.btn_workout_start)
        buttonSet = findViewById(R.id.btn_workout_button_set)
        pauseButton = findViewById(R.id.btn_workout_pause)
        buttonDone = findViewById(R.id.btn_workout_done)
        buttonDoneAct = findViewById(R.id.btn_workout_done_act)
        showExp = findViewById(R.id.workout_exp)

        initUI()
    }

    fun initUI() {
        Glide.with(this).asGif().load(R.raw.pixel_best).into(iv_workout_detail)
        if (intent.hasExtra("workout_name")) {
            val str = intent.getStringExtra("workout_name")
            tool_workout_name.text = str
            when (str) {
                "축구" -> Glide.with(this).asGif().load(R.raw.pixel_best).into(iv_workout_detail)
                "자전거" -> Glide.with(this).asGif().load(R.raw.pixel_best).into(iv_workout_detail)
                "수영" -> Glide.with(this).asGif().load(R.raw.pixel_best).into(iv_workout_detail)
            }
        }
        bindViews()
    }

    private fun bindViews() {
        hour = findViewById(R.id.workout_hour)
        minute = findViewById(R.id.workout_minute)
        seconds = findViewById(R.id.workout_seconds)

        startButton?.setOnClickListener {
            if (layoutFlag == 1) {//start
                startButton?.visibility = View.INVISIBLE
                buttonSet?.visibility = View.VISIBLE
                pauseButton?.setImageResource(R.drawable.btn_workout_pause)
                StartTime = SystemClock.uptimeMillis()
                handler?.postDelayed(runnable, 0)
                layoutFlag = 2
            }
        }

        pauseButton?.setOnClickListener {
            buttonFlag = when {
                layoutFlag == 2 && buttonFlag -> {//2번째 상태
                    pauseButton?.setImageResource(R.drawable.btn_workout_start)
                    handler?.removeCallbacks(runnable)//
                    false
                }
                else -> {//3번째 상태
                    pauseButton?.setImageResource(R.drawable.btn_workout_pause)
                    handler?.postDelayed(runnable, 0)
                    true
                }
            }
        }

        buttonDone?.setOnClickListener {
            layoutFlag = 3
            handler?.removeCallbacks(runnable)
            buttonSet?.visibility = View.INVISIBLE
            buttonDoneAct?.visibility = View.VISIBLE
            showExp?.visibility = View.VISIBLE

            //TODO 서버로 시간 보내기
            Toast.makeText(applicationContext, (MillisecondTime/1000).toString(), Toast.LENGTH_SHORT).show()
            //TODO 경험치창 보여주기

            //TODO 이후 3초 있다가 화면 없애기
            handler?.postDelayed(finish, 3000)
        }
        handler = Handler()
    }

    private var finish : Runnable = object : Runnable{
        override fun run() {
            finish()
        }
    }

    private var runnable: Runnable = object : Runnable {
        override fun run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime
            UpdateTime = TimeBuff + MillisecondTime
            Seconds = (UpdateTime / 1000).toInt()
            Minutes = Seconds / 60
            Seconds = Seconds % 60
            if (Minutes.toString().length < 2) {
                minute?.text = "0" + Minutes.toString()
            } else {
                minute?.text = Minutes.toString()
            }
            if (Seconds.toString().length < 2) {
                seconds?.text = "0" + Seconds.toString()
            } else {
                seconds?.text = Seconds.toString()
            }
            handler?.postDelayed(this, 0)
        }
    }
}
