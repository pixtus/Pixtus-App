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
import android.util.Log
import com.bumptech.glide.Glide
import com.mash.up.pixtus_app.*
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment(), SensorEventListener {
    var handler: Handler? = null
    var sensorManager: SensorManager? = null
    var stepCounterSensor: Sensor? = null
    var count = 0f
    var start_count: Float? = null
    var stop_count: Float? = null
    var root: View ?= null

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        Glide.with(this).asGif().load(R.raw.nomal1).into(root!!.iv_gif)
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

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.activity_main, container, false)
        Glide.with(this).asGif().load(R.raw.nomal1).into(root!!.iv_gif)
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

        return root
    }

    fun setData(model: MainResponse) {
        tv_title.text = model.characterName
        tv_calorie.text = model.exp.toString()
        tv_next_calorie.text = model.nextExp.toString()
        when(model.level){
            1 ->{
                Glide.with(this).asGif().load(R.raw.pixtus_ani_01_walk).into(iv_gif)
            }
            2 ->{
                Glide.with(this).asGif().load(R.raw.pixtus_ani_02_walk).into(iv_gif)
            }
            3->{
                Glide.with(this).asGif().load(R.raw.pixtus_ani_03_walk).into(iv_gif)
            }
        }

        var sortedList = model.workouts.sortedWith(compareByDescending { it.totalKcal })

        /*
        sortedList = listOf(
            WorkoutX("걷기", 2300),
            WorkoutX("달리기", 3300),WorkoutX("수영", 4300),
            WorkoutX("축구", 5300),WorkoutX("자전거", 6300),
            WorkoutX("축구", 5300),WorkoutX("자전거", 6300),
            WorkoutX("축구", 5300),WorkoutX("자전거", 6300))
        */

        var exercise_recycler = root!!.findViewById(R.id.recycler_exercise) as RecyclerView
        exercise_recycler.adapter = ExerciseAdapter(sortedList)
        exercise_recycler.layoutManager = LinearLayoutManager(activity)
    }
}