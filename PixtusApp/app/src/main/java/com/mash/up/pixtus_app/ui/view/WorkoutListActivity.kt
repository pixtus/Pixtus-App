package com.mash.up.pixtus_app.ui.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.RecyclerViewAdapter
import com.mash.up.pixtus_app.core.Excercises
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_workout_list.*

class WorkoutListActivity : AppCompatActivity() {
    var list: List<Excercises> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list)
        initList()

        NetworkCore.getNetworkCore<PixtusApi>()
            .getExcercises()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                list = it
            }, {
                it.printStackTrace()
            })

        btn_workout_back.setOnClickListener {
            finish()
        }
    }

    fun initList() {
        rv_workout_list.apply {
            layoutManager = LinearLayoutManager(this@WorkoutListActivity)
            adapter = RecyclerViewAdapter(list)
        }
    }
}
