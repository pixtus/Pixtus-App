package com.mash.up.pixtus_app

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mash.up.pixtus_app.core.Excercises
import com.mash.up.pixtus_app.ui.view.WorkoutDetailActivity

class RecyclerViewAdapter(val list: List<Excercises>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(excercises: Excercises) {
            if(excercises.name.equals("축구")) view.findViewById<ImageView>(R.id.img_workoutlist).setImageResource(R.drawable.thumnail_soccer)
            if(excercises.name.equals("수영")) view.findViewById<ImageView>(R.id.img_workoutlist).setImageResource(R.drawable.thumnail_swim)
            if(excercises.name.equals("자전거")) view.findViewById<ImageView>(R.id.img_workoutlist).setImageResource(R.drawable.thumnail_bike)
            view.findViewById<TextView>(R.id.tv_workout_name).text = excercises.name
            view.findViewById<TextView>(R.id.tv_workout_kcal).text = excercises.kcal.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position])
        holder.view.setOnClickListener {

            val nextIntent = Intent(holder.view.context, WorkoutDetailActivity::class.java)
            nextIntent.putExtra("workout_id", list.get(position).exerciseId)
            nextIntent.putExtra("workout_name", list.get(position).name)
            holder.view.context.startActivity(nextIntent)
        }
    }
}