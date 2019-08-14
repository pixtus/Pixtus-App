package com.mash.up.pixtus_app.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle

import android.os.Handler
import com.bumptech.glide.Glide

import android.support.v7.widget.LinearLayoutManager

import com.mash.up.pixtus_app.ExerciseAdapter

import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity(), SensorEventListener {
    var handler: Handler? = null
    var sensorManager: SensorManager? = null
    var stepCounterSensor: Sensor? = null
    var count = 0f
    var start_count: Float? = null
    var stop_count: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI)
        //TODO background에서 돈 결과 서버로 보내주기
        //저장된 start_count가져오기
        //서버에 count - start_count보내기
        stop_count = count
    }

    override fun onPause() {
        super.onPause()
//        sensorManager?.unregisterListener(this)
    }

    override fun onStop() {
        super.onStop()
        //TODO 어플 꺼지는 시점 걸음수 서버에 보내기
        start_count = count//start_count저장해두기
        //서버에 event.value - stop_count 보내기
    }

    fun initUI() {
        Glide.with(this).asGif().load(R.raw.nomal1).into(iv_gif)
        var dateFormat = SimpleDateFormat("MM.dd / EEE")
        tv_date.text = dateFormat.format(Date()).toString()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounterSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        handler = Handler()

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_STEP_COUNTER) {
            if(event.values[0] - count > 0) {
                count = event.values[0]
                Glide.with(this).asGif().load(R.raw.walk1).into(iv_gif)
                handler?.postDelayed(setImage, 2500)
            }
        }
    }

    private var setImage: Runnable = Runnable {
        Glide.with(this).asGif().load(R.raw.nomal1).into(iv_gif)
    }
}