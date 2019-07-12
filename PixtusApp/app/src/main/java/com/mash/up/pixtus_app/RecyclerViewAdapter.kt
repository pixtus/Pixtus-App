package com.mash.up.pixtus_app

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class RecyclerViewAdapter(val list: MutableList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(WorkOut: WorkOut) {
            //view.productImage.setImageResource(R.mipmap.ic_launcher)//kotlin
            view.findViewById<ImageView>(R.id.img_workoutlist).setImageResource(WorkOut.image)
            view.findViewById<TextView>(R.id.tv_workout_name).text = (WorkOut.name)
            view.findViewById<TextView>(R.id.tv_workout_kcal).text = WorkOut.kcal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position] as WorkOut)
    }
}