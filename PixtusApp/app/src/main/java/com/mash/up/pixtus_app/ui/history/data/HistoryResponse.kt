package com.mash.up.pixtus_app.ui.history.data


/**
 * Created by TakHyeongMin on 2019-08-23.
 */
data class HistoryResponse(
    val mealHistory: List<MealHistory>,
    val workoutHistory: List<WorkoutHistory>
)