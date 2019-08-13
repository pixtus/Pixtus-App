package com.mash.up.pixtus_app.ui.history.data

data class HistoryResponse(
    val mealHistory: List<MealHistory>,
    val workoutHistory: List<WorkoutHistory>
)