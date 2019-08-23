package com.mash.up.pixtus_app.ui.login.data

data class PostLoginResponse(
    val characterName: String,
    val date: String,
    val exp: Int,
    val level: Int,
    val nextExp: Int,
    val workouts: List<Workout>
)