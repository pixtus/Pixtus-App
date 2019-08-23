package com.mash.up.pixtus_app.ui.create.data

data class CreateAcoountRequest(
    val characterName: String,
    val email: String,
    val gender: String,
    val height: Int,
    val name: String,
    val uid: String,
    val weight: Int
)