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
import android.text.Layout
import com.bumptech.glide.Glide
import com.mash.up.pixtus_app.ExerciseAdapter
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment(), SensorEventListener {
    var handler: Handler? = null
    var sensorManager: SensorManager? = null
    var stepCounterSensor: Sensor? = null
    var count = 0f
    var root: View? = null
    var recoard: Float = 0f

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_STEP_COUNTER) {
            Glide.with(this).asGif().load(R.raw.walk1).into(iv_gif)
            handler?.postDelayed(setImage, 2500)
        }
    }

    private var setImage: Runnable = Runnable {
        Glide.with(this).asGif().load(R.raw.nomal1).into(root!!.iv_gif)
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI)
        //TODO background에서 돈 결과 서버로 보내주기
        //저장된 start_count가져오기
        //서버에 count - start_count보내기
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