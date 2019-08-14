package com.mash.up.pixtus_app

data class MainResponse(
    val characterName: String,
    val date: String,
    val exp: Int,
    val level: Int,
    val nextExp: Int,
    val workouts: List<WorkoutX>
)