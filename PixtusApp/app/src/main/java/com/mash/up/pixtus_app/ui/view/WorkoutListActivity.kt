package com.mash.up.pixtus_app.ui.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.Resource
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.RecyclerViewAdapter
import com.mash.up.pixtus_app.WorkOut
import kotlinx.android.synthetic.main.activity_workout_list.*

class WorkoutListActivity : AppCompatActivity() {
    val list: MutableList<Any> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list)
        initList()

        btn_workout_back.setOnClickListener {
            finish()
        }
    }

    fun initList() {
        list.add(WorkOut(R.drawable.workout_soccer, "축구", "1234kcal"))
        list.add(WorkOut(R.drawable.workout_bike, "자전거", "1234kcal"))
        list.add(WorkOut(R.drawable.workout_swim, "수영", "1234kcal"))

        rv_workout_list.apply {
            layoutManager = LinearLayoutManager(this@WorkoutListActivity)
            adapter = RecyclerViewAdapter(list)
        }

    }
}
