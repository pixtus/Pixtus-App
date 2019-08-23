package com.mash.up.pixtus_app.ui.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.constraint.ConstraintLayout
import android.util.Log
import kotlinx.android.synthetic.main.activity_workout_detail.*
import android.view.View
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import com.mash.up.pixtus_app.data.StepData
import com.mash.up.pixtus_app.utils.SharedPreferenceController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class WorkoutDetailActivity : AppCompatActivity() {
    var handler: Handler? = null
    var hour: TextView? = null
    var minute: TextView? = null
    var seconds: TextView? = null

    internal var MillisecondTime: Long = 0
    internal var StartTime: Long = 0
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
    var bar_workout_exp : ProgressBar ?= null

    var stepData = StepData(MillisecondTime.toFloat(), 1)

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
        bar_workout_exp = findViewById(R.id.bar_workout_exp)



        initUI()
    }

    fun initUI() {
        val animationView = findViewById<LottieAnimationView>(R.id.lottie_workout_detail)
        //Glide.with(this).asGif().load(R.raw.pixel_best).into(iv_workout_detail)
        if (intent.hasExtra("workout_name")) {
            val str = intent.getStringExtra("workout_name")
            tool_workout_name.text = str
            when (str) {//운동에 따른 이미지
                "축구" -> animationView.setAnimation("ic_check.json")
                "자전거" -> animationView.setAnimation("pixtus_workout_bicycle.json")
                "수영" -> animationView.setAnimation("pixtus_workout_swim.json")
            }
            animationView.loop(true)
            animationView.playAnimation()
        }
        if (intent.hasExtra("workout_id")){
            stepData.exerciseId = intent.getIntExtra("workout_id", 0)
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
                else -> {
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

            stepData.amount = (MillisecondTime/1000).toFloat()

            NetworkCore.getNetworkCore<PixtusApi>()
                .sendExercise(
                    SharedPreferenceController.getAuthorization(this@WorkoutDetailActivity),
                    stepData
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tv_workout_addexp.text = it.exp.toString()
                    tv_workout_pre_exp.text = it.currExp.toString()
                    tv_workout_total_exp.text = it.nextExp.toString()
                }, {
                    Log.d("send_step", "fail")
                })
            handler?.postDelayed(finish, 3000)
        }
        handler = Handler()
    }

    private var finish : Runnable = Runnable { finish() }

    private var runnable: Runnable = object : Runnable {
        override fun run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime
            UpdateTime = TimeBuff + MillisecondTime
            Seconds = (UpdateTime / 1000).toInt()
            Minutes = Seconds / 60
            Seconds %= 60
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
