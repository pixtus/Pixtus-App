package com.example.stepcount

import android.content.Context
import android.content.SharedPreferences
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
import android.widget.ProgressBar
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.mash.up.pixtus_app.ExerciseAdapter
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.core.MainResponse
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import com.mash.up.pixtus_app.data.StepData
import com.mash.up.pixtus_app.utils.SharedPreferenceController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(), SensorEventListener {
    var sensorManager: SensorManager? = null
    var sensorManagerShake: SensorManager ?= null
    var stepDetectorSensor: Sensor? = null
    var shakeSensor: Sensor? = null
    var count = 0f
    var root: View? = null
    var preferences:SharedPreferences ?= null
    var editor: SharedPreferences.Editor ?= null
    var remember: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = context!!.getSharedPreferences("STEP_COUNT", Context.MODE_PRIVATE)
        editor = preferences!!.edit()
        sendData()
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.activity_main, container, false)
        var dateFormat = SimpleDateFormat("MM.dd / EEE")
        root!!.tv_date.text = dateFormat.format(Date()).toString()

        initUI()
        getData()
        return root
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManagerShake?.registerListener(this, shakeSensor, SensorManager.SENSOR_DELAY_UI)
        getData()
    }

    override fun onPause() {
        super.onPause()

        editor!!.putFloat("stepCount", count)
        editor!!.commit()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_STEP_DETECTOR) {
            if (event.values[0] == 1.0f) {
                count++
                remember = count
            }
        }else if (event!!.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            val animationView = root!!.findViewById<LottieAnimationView>(R.id.lottie_main)
            animationView.setAnimation("pixtus_walk_junior.json")
            if(event.values[0] > 3){
                animationView.playAnimation()
            }
        }
    }


    fun initUI() {
        sensorManager = this.activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepDetectorSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        sensorManagerShake = this.activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        shakeSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    fun setData(model: MainResponse) {
        val animationView = root!!.findViewById<LottieAnimationView>(R.id.lottie_main)
        tv_title.text = model.characterName
        tv_calorie.text = model.exp.toString()
        tv_next_calorie.text = model.nextExp.toString()
        when (model.level) {
            1 -> {
                animationView.setAnimation("pixtus_walk_junior.json")
            }
            2 -> {
                animationView.setAnimation("pixtus_walk_senior.json")
            }
            3 -> {
                animationView.setAnimation("pixtus_walk_master.json")
            }
        }

        animationView.playAnimation()
        animationView.pauseAnimation()
        var sortedList = model.workouts.sortedWith(compareByDescending { it.totalKcal })
        var exercise_recycler = root!!.findViewById(R.id.recycler_exercise) as RecyclerView
        exercise_recycler.adapter = ExerciseAdapter(sortedList)
        exercise_recycler.layoutManager = LinearLayoutManager(activity)
    }

    fun sendData(){
        remember = preferences!!.getFloat("stepCount", 0.0f)
        NetworkCore.getNetworkCore<PixtusApi>()
            .sendStep(
                SharedPreferenceController.getAuthorization(context!!),
                StepData(remember,1)
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getData()
            }, {
            })
    }

    fun getData(){
        NetworkCore.getNetworkCore<PixtusApi>()
            .getMain(
                SharedPreferenceController.getAuthorization(context!!)
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setData(it)
                stats_bar.progress = ((it.exp * 100)/it.nextExp)
                Log.d("경험치", ((it.exp * 100)/it.nextExp).toString())
            }, {
            })
    }
}