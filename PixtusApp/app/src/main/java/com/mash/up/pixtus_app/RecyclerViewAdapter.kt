package com.mash.up.pixtus_app

import android.content.Context
import android.content.Intent
import android.support.v7.view.menu.MenuAdapter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.mash.up.pixtus_app.data.Exercise
import com.mash.up.pixtus_app.ui.view.WorkoutDetailActivity

class RecyclerViewAdapter(val list: List<Exercise>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(Exercise: Exercise) {
            if (Exercise.name.equals("축구")){
                view.findViewById<ImageView>(R.id.img_workoutlist).setImageResource(R.drawable.workout_soccer)
            }else if(Exercise.name.equals("수영")){
                view.findViewById<ImageView>(R.id.img_workoutlist).setImageResource(R.drawable.workout_swim)
            }else if(Exercise.name.equals("자전거")){
                view.findViewById<ImageView>(R.id.img_workoutlist).setImageResource(R.drawable.workout_bike)
            }else {
                view.findViewById<ImageView>(R.id.img_workoutlist).setImageResource(R.drawable.img_walk1)
            }
//            view.findViewById<ImageView>(R.id.img_workoutlist).setImageResource(WorkOut.image)
            view.findViewById<TextView>(R.id.tv_workout_name).text = Exercise.name
            view.findViewById<TextView>(R.id.tv_workout_kcal).text = (Exercise.measure * 60).toString() + "kcal"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position] as Exercise)
        holder.view.setOnClickListener {
            val nextIntent = Intent(holder.view.context, WorkoutDetailActivity::class.java)
            nextIntent.putExtra("workout_name", list.get(position).name)
            nextIntent.putExtra("workout_id", list.get(position).exerciseId)
            holder.view.context.startActivity(nextIntent)
        }
    }
}