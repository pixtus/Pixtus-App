package com.mash.up.pixtus_app

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_workout_main.view.*

class ExerciseAdapter(sortedList : List<WorkoutX>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>(){

    var items : List<WorkoutX> = sortedList
    /*
    var items : MutableList<WorkOutMain> = mutableListOf(WorkOutMain("걷기", "2300"),
        WorkOutMain("달리기", "3300"),WorkOutMain("수영", "4300"),
        WorkOutMain("축구", "5300"),WorkOutMain("자전거", "6300"),
        WorkOutMain("축구", "5300"),WorkOutMain("자전거", "6300"),
        WorkOutMain("축구", "5300"),WorkOutMain("자전거", "6300"))
    */

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ExerciseViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        items[position].let{item->
            with(holder){
                tv_workout_name_main.text=item.exerciseName
                tv_workout_name_kcal.text=item.totalKcal.toString()
            }
        }
    }

    inner class ExerciseViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_workout_main, parent, false)){
        val tv_workout_name_main = itemView.tv_workout_name_main
        val tv_workout_name_kcal = itemView.tv_workout_kcal_main
    }
}