package com.mash.up.pixtus_app.core

import com.mash.up.pixtus_app.data.Workout

data class MainResponse(
    val characterName: String,
    val date: String,
    val exp: Int,
    val level: Int,
    val nextExp: Int,
    val workouts: List<Workout>
)