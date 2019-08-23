package com.mash.up.pixtus_app.ui.history.data

import com.mash.up.pixtus_app.ui.history.fragment.HistoryGetTabFragment


/**
 * Created by TakHyeongMin on 2019-08-20.
 */
data class History(
    val weekString: String,
    var totalGetKcal: Int,
    var totalUseKcal: Int,
    var totalWalkKcal: Int,
    var totalSwimKcal: Int,
    var totalRunKcal: Int,
    var totalBikeKcal: Int,
    var totalBreakFastKcal: Int,
    var totalLunchKcal: Int,
    var totalDinnerKcal: Int,
    var totalYasikKcal: Int,
    // 일 ~ 토
    val dayKcalList: ArrayList<DayKcal>
)

// 일 ~ 토 중 하루 정보
data class DayKcal(
    val weekAday: String,
    val date: Int,
    var dayTotalGetKacl: Int,
    var dayTotalUseKacl: Int,
    val mealHistoryList: ArrayList<MealHistory>,
    val workoutHistory: ArrayList<WorkoutHistory>
)
