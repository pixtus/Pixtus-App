package com.example.stepcount

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.annotation.Nullable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.bumptech.glide.Glide
import com.mash.up.pixtus_app.ExerciseAdapter
import com.mash.up.pixtus_app.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment(), SensorEventListener {
    var sensorManager: SensorManager? = null
    var stepDetectorSensor: Sensor? = null
    var handler: Handler? = null
    var root: View? = null

    fun initUI() {
        sensorManager = this.activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepDetectorSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        Glide.with(this).asGif().load(R.raw.nomal1).into(iv_gif)
        if(event!!.sensor.type == Sensor.TYPE_STEP_DETECTOR){
            if (event.values[0] == 1.0f){
                //TODO 로티 삽입
                Log.d("하하하", "들어옴")
                Glide.with(this).asGif().load(R.raw.walk1).into(root!!.iv_gif)
                handler?.postDelayed(Runnable { Glide.with(this).asGif().load(R.raw.nomal1).into(root!!.iv_gif) }, 2500)
                Log.d("하하하", "나감")
            }
            Log.d("하하하", "나옴")
        }
    }

    var setImage : Runnable = Runnable {
        Glide.with(this).asGif().load(R.raw.nomal1).into(iv_gif)
    }


    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_UI)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.activity_main, container, false)
        Glide.with(this).asGif().load(R.raw.nomal1).into(root!!.iv_gif)
        var dateFormat = SimpleDateFormat("MM.dd / EEE")
        root!!.tv_date.text = dateFormat.format(Date()).toString()
        var exercise_recycler: RecyclerView

        exercise_recycler = root!!.findViewById(R.id.recycler_exercise) as RecyclerView
        exercise_recycler.adapter = ExerciseAdapter()
        exercise_recycler.layoutManager = LinearLayoutManager(activity)
        return root
    }
}