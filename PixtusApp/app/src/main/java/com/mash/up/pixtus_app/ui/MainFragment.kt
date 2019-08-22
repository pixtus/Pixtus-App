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
import com.mash.up.pixtus_app.core.MainResponse
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import com.mash.up.pixtus_app.data.StepData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(), SensorEventListener {
    var handler: Handler? = null
    var sensorManager: SensorManager? = null
    var stepDetectorSensor: Sensor? = null
    var count = 0f
    var root: View? = null
    var stepData = StepData(count, 1)

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_STEP_DETECTOR) {
            if (event.values[0] == 1.0f) {
                count++
                //TODO 로티 삽입
                Glide.with(this).asGif().load(R.raw.walk1).into(root!!.iv_gif)
                handler?.postDelayed(Runnable { Glide.with(this).asGif().load(R.raw.nomal1).into(root!!.iv_gif) }, 3000)
                //view!!.tv_title.text = count.toString()
            }
            stepData!!.amount = count
        }
    }

    private var setImage: Runnable = Runnable {
        Glide.with(this).asGif().load(R.raw.nomal1).into(root!!.iv_gif)
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkCore.getNetworkCore<PixtusApi>()
            .sendStep(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwidWlkIjoiMTIzNCJ9.KRCUrR_TqDXXfVnAxSIsQ17E8GtvOewPZCh9GOtFJVY",
                stepData
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("send_step", "success")
            }, {
                Log.d("send_step", "fail")
            })
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.activity_main, container, false)
        //Glide.with(this).asGif().load(R.raw.nomal1).into(root!!.iv_gif)
        var dateFormat = SimpleDateFormat("MM.dd / EEE")
        root!!.tv_date.text = dateFormat.format(Date()).toString()

        NetworkCore.getNetworkCore<PixtusApi>()
            .getMain(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwidWlkIjoiMTIzNCJ9.KRCUrR_TqDXXfVnAxSIsQ17E8GtvOewPZCh9GOtFJVY"
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setData(it)
                Log.d("list_data", it.toString())
            }, {
                Log.d("list_data", Log.getStackTraceString(it))
            })

        initUI()

        return root
    }

    override fun onPause() {
        super.onPause()
        Log.d("onpause", "")
    }

    fun initUI() {
        sensorManager = this.activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepDetectorSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

    }


    fun setData(model: MainResponse) {
        tv_title.text = model.characterName
        tv_calorie.text = model.exp.toString()
        tv_next_calorie.text = model.nextExp.toString()
        when (model.level) {
            1 -> {
                Glide.with(this).asGif().load(R.raw.nomal1).into(iv_gif)
            }
            2 -> {
                Glide.with(this).asGif().load(R.raw.pixtus_ani_02_walk).into(iv_gif)
            }
            3 -> {
                Glide.with(this).asGif().load(R.raw.pixtus_ani_03_walk).into(iv_gif)
            }
        }

        var sortedList = model.workouts.sortedWith(compareByDescending { it.totalKcal })

        var exercise_recycler = root!!.findViewById(R.id.recycler_exercise) as RecyclerView
        exercise_recycler.adapter = ExerciseAdapter(sortedList)
        exercise_recycler.layoutManager = LinearLayoutManager(activity)
    }
}