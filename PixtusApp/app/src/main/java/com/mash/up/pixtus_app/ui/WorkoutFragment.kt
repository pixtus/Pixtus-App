package com.example.stepcount

import android.os.Bundle
import android.os.Handler
import android.support.annotation.Nullable
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mash.up.pixtus_app.ExerciseAdapter
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.RecyclerViewAdapter
import com.mash.up.pixtus_app.WorkOut
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import com.mash.up.pixtus_app.data.Exercise
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_main.view.recycler_exercise
import kotlinx.android.synthetic.main.activity_workout_list.*
import kotlinx.android.synthetic.main.activity_workout_list.view.*
import java.text.SimpleDateFormat
import java.util.*

class WorkoutFragment : Fragment() {
    var root: View? = null
    var list: List<Exercise> = arrayListOf()


    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.activity_workout_list, container, false)
        getExcerciseList()

        return root
    }

    fun getExcerciseList() {
        NetworkCore.getNetworkCore<PixtusApi>()
            .getExcercises("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwidWlkIjoiMTIzNCJ9.KRCUrR_TqDXXfVnAxSIsQ17E8GtvOewPZCh9GOtFJVY")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                root!!.rv_workout_list.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = RecyclerViewAdapter(it)
                }
            }, {
                it.printStackTrace()
            })
    }

//    fun initList() {
//        list.add(WorkOut(R.drawable.workout_soccer, "축구", "1234kcal"))
//        list.add(WorkOut(R.drawable.workout_bike, "자전거", "1234kcal"))
//        list.add(WorkOut(R.drawable.workout_swim, "수영", "1234kcal"))
//
//        root!!.rv_workout_list.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = RecyclerViewAdapter(list)
//        }
//
//    }
}