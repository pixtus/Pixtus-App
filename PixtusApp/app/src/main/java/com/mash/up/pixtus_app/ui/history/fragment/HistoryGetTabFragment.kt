package com.mash.up.pixtus_app.ui.history.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.core.HistoryApi
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.ui.history.data.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_history_get_tab.*
import com.mash.up.pixtus_app.utils.DateUtils
import kotlin.collections.ArrayList


/**
 * Created by TakHyeongMin on 2019-07-25.
 */
class HistoryGetTabFragment : Fragment() {

    private var prevWeek: Int = 0
    private var presentWeek: String? = ""
    private val d: Float by lazy { context!!.resources.displayMetrics.density }
    lateinit var historyResponse: HistoryResponse



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_history_get_tab, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        getHistory(2)
        initPresentWeekTv()
//        val historyResponse = HistoryResponse(
//            listOf(
//                MealHistory(
//                    "20190818",
//                    300,
//                    "B"
//                ),
//                MealHistory(
//                    "20190818",
//                    500,
//                    "L"
//                ),
//                MealHistory(
//                    "20190818",
//                    700,
//                    "D"
//                ),
//                MealHistory(
//                    "20190818",
//                    700,
//                    "M"
//                ),
//
//                MealHistory(
//                    "20190819",
//                    400,
//                    "B"
//                ),
//
//                MealHistory(
//                    "20190819",
//                    700,
//                    "L"
//                ),
//                MealHistory(
//                    "20190819",
//                    900,
//                    "D"
//                ),
//                MealHistory(
//                    "20190820",
//                    700,
//                    "B"
//                ),
//                MealHistory(
//                    "20190820",
//                    700,
//                    "L"
//                ),
//                MealHistory(
//                    "20190820",
//                    700,
//                    "D"
//                ),
//                MealHistory(
//                    "20190820",
//                    700,
//                    "M"
//                )
//            ),
//            listOf(
//                WorkoutHistory(
//                    "20190818",
//                    "걷기",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190818",
//                    "수영",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190818",
//                    "달리기",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190818",
//                    "자전거",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190819",
//                    "걷기",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190819",
//                    "수영",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190819",
//                    "자전거",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190819",
//                    "달리기",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190820",
//                    "자전거",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190820",
//                    "걷기",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190820",
//                    "수영",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190821",
//                    "수영",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190822",
//                    "수영",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190823",
//                    "수영",
//                    400
//                ),
//                WorkoutHistory(
//                    "20190824",
//                    "수영",
//                    400
//                )
//            )
//        )

    }

    /**
     * @param preweek : 이번주일 경우 0, 일주일 전 경우 1, 이주 전 경우 2
     * 통신함수
     */
    private fun getHistory(preweek: Int) {
        NetworkCore.getNetworkCore<HistoryApi>()
            .getHistory(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwidWlkIjoiMTIzNCJ9.KRCUrR_TqDXXfVnAxSIsQ17E8GtvOewPZCh9GOtFJVY",
                preweek
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // 성공했을 때
                Log.d("list_data", it.toString())
                historyResponse = it
                reformHistoryResponse(historyResponse)
            }, {
                // 실패했을 때
                Log.d("list_data", Log.getStackTraceString(it))
            })
    }

    private fun setOnClickListener() {
        btn_left.setOnClickListener {
            prevWeek++
            tv_week.text = presentWeek
        }

        btn_right.setOnClickListener {
            if (prevWeek > 0)
                prevWeek--
            tv_week.text = DateUtils().getWeek(prevWeek)
        }
    }

    private fun initPresentWeekTv() {
        presentWeek = DateUtils().getWeek(prevWeek)
        tv_week.text = DateUtils().getWeek(prevWeek)
    }

    /**
     * @param historyResponse : getHistory() 통신의 결과값
     * 통신의 결과값을 View에 뿌리기 적합한 데이터로 reform
     */
    private fun reformHistoryResponse(historyResponse: HistoryResponse) {

        val history = createHistory()

        val dateUtils = DateUtils()

        for (mealHistory in historyResponse.mealHistory) {
            // 해당 요일가져오기
            // dayKcalList[0] ~ [6] 까지는 일월화수목금토를 의미
            when (dateUtils.getDayOfWeek(mealHistory.dateId)) {
                "일" -> {
                    history.dayKcalList[0].mealHistoryList.add(mealHistory)
                    history.dayKcalList[0].dayTotalGetKacl += mealHistory.kcal
                }
                "월" -> {
                    history.dayKcalList[1].mealHistoryList.add(mealHistory)
                    history.dayKcalList[1].dayTotalGetKacl += mealHistory.kcal
                }
                "화" -> {
                    history.dayKcalList[2].mealHistoryList.add(mealHistory)
                    history.dayKcalList[2].dayTotalGetKacl += mealHistory.kcal
                }
                "수" -> {
                    history.dayKcalList[3].mealHistoryList.add(mealHistory)
                    history.dayKcalList[3].dayTotalGetKacl += mealHistory.kcal
                }
                "목" -> {
                    history.dayKcalList[4].mealHistoryList.add(mealHistory)
                    history.dayKcalList[4].dayTotalGetKacl += mealHistory.kcal
                }
                "금" -> {
                    history.dayKcalList[5].mealHistoryList.add(mealHistory)
                    history.dayKcalList[5].dayTotalGetKacl += mealHistory.kcal
                }
                "토" -> {
                    history.dayKcalList[6].mealHistoryList.add(mealHistory)
                    history.dayKcalList[6].dayTotalGetKacl += mealHistory.kcal
                }
            }
            history.totalGetKcal += mealHistory.kcal
            when (mealHistory.type) {
                // 아침
                "B" -> history.totalBreakFastKcal += mealHistory.kcal
                // 점심
                "L" -> history.totalLunchKcal += mealHistory.kcal
                // 저녁
                "D" -> history.totalDinnerKcal += mealHistory.kcal
                // 간식
                "M" -> history.totalYasikKcal += mealHistory.kcal
            }
        }

        for (workOutHistory in historyResponse.workoutHistory) {
            // 해당 요일가져오기
            // dayKcalList[0] ~ [6] 까지는 일월화수목금토를 의미
            when (dateUtils.getDayOfWeek(workOutHistory.date)) {
                "일" -> {
                    history.dayKcalList[0].workoutHistory.add(workOutHistory)
                    history.dayKcalList[0].dayTotalUseKacl += workOutHistory.totalKcal
                }
                "월" -> {
                    history.dayKcalList[1].workoutHistory.add(workOutHistory)
                    history.dayKcalList[1].dayTotalUseKacl += workOutHistory.totalKcal
                }
                "화" -> {
                    history.dayKcalList[2].workoutHistory.add(workOutHistory)
                    history.dayKcalList[2].dayTotalUseKacl += workOutHistory.totalKcal
                }
                "수" -> {
                    history.dayKcalList[3].workoutHistory.add(workOutHistory)
                    history.dayKcalList[3].dayTotalUseKacl += workOutHistory.totalKcal
                }
                "목" -> {
                    history.dayKcalList[4].workoutHistory.add(workOutHistory)
                    history.dayKcalList[4].dayTotalUseKacl += workOutHistory.totalKcal
                }
                "금" -> {
                    history.dayKcalList[5].workoutHistory.add(workOutHistory)
                    history.dayKcalList[5].dayTotalUseKacl += workOutHistory.totalKcal
                }
                "토" -> {
                    history.dayKcalList[6].workoutHistory.add(workOutHistory)
                    history.dayKcalList[6].dayTotalUseKacl += workOutHistory.totalKcal
                }
            }
            history.totalUseKcal += workOutHistory.totalKcal
            when (workOutHistory.exerciseName) {
                "걷기" -> history.totalWalkKcal += workOutHistory.totalKcal
                "달리기" -> history.totalRunKcal += workOutHistory.totalKcal
                "수영" -> history.totalSwimKcal += workOutHistory.totalKcal
                "자전거" -> history.totalBikeKcal += workOutHistory.totalKcal
            }
        }

        setGraph(history)
    }

    /**
     * 뷰에 뿌리기 적합한 데이터 클래스인 History 객체 생성
     * @return 생성한 History 객체
     */
    private fun createHistory(): History {
        val history =
            History(
                "", 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0,
                arrayListOf(
                    DayKcal(
                        "일", 0, 0, 0,
                        ArrayList<MealHistory>()
                        , ArrayList<WorkoutHistory>()
                    ),
                    DayKcal(
                        "월", 0, 0, 0,
                        ArrayList<MealHistory>(),
                        ArrayList<WorkoutHistory>()
                    ),
                    DayKcal(
                        "화", 0, 0, 0,
                        ArrayList<MealHistory>(),
                        ArrayList<WorkoutHistory>()
                    ),
                    DayKcal(
                        "수", 0, 0, 0,
                        ArrayList<MealHistory>(),
                        ArrayList<WorkoutHistory>()
                    ),
                    DayKcal(
                        "목", 0, 0, 0,
                        ArrayList<MealHistory>(),
                        ArrayList<WorkoutHistory>()
                    ),
                    DayKcal(
                        "금", 0, 0, 0,
                        ArrayList<MealHistory>(),
                        ArrayList<WorkoutHistory>()
                    ),
                    DayKcal(
                        "토", 0, 0, 0,
                        ArrayList<MealHistory>(),
                        ArrayList<WorkoutHistory>()
                    )
                )
            )

        return history
    }

    /**
     * @param History 객체
     *
     * History 데이터를 받아서 그래프에 뿌리기
     */
    private fun setGraph(history: History) {
        tv_total_get_kcal.text = history.totalGetKcal.toString()
        tv_total_breakfast.text = history.totalBreakFastKcal.toString()
        tv_total_lunch.text = history.totalLunchKcal.toString()
        tv_total_dinner.text = history.totalDinnerKcal.toString()
        tv_total_yasic.text = history.totalYasikKcal.toString()


        var index = 0
        for (dayKcalItem in history.dayKcalList) {
            when (index) {
                // 일
                0 -> {
                    setBottomMargin(total_use_kcal_sunday, dayKcalItem.dayTotalUseKacl)
                    setStickGraph(tv_sun_breakfast, tv_sun_lunch, tv_sun_dinner, tv_sun_midnight_snack, dayKcalItem.mealHistoryList)
                }
                // 월
                1 -> {
                    setBottomMargin(total_use_kcal_monday, dayKcalItem.dayTotalUseKacl)
                    setStickGraph(tv_mon_breakfast, tv_mon_lunch, tv_mon_dinner, tv_mon_midnight_snack, dayKcalItem.mealHistoryList)
                }
                // 화
                2 -> {
                    setBottomMargin(total_use_kcal_tuesday, dayKcalItem.dayTotalUseKacl)
                    setStickGraph(tv_tues_breakfast, tv_tues_lunch, tv_tues_dinner, tv_tues_midnight_snack, dayKcalItem.mealHistoryList)
                }
                // 수
                3 -> {
                    setBottomMargin(total_use_kcal_wedns_day, dayKcalItem.dayTotalUseKacl)
                    setStickGraph(tv_wedns_breakfast, tv_wedns_lunch, tv_wedns_dinner, tv_wedns_midnight_snack, dayKcalItem.mealHistoryList)
                }
                // 목
                4 -> {
                    setBottomMargin(total_use_kcal_thursday, dayKcalItem.dayTotalUseKacl)
                    setStickGraph(tv_thurs_breakfast, tv_thurs_lunch, tv_thurs_dinner, tv_thurs_midnight_snack, dayKcalItem.mealHistoryList)
                }
                // 금
                5 -> {
                    setBottomMargin(total_use_kcal_friday, dayKcalItem.dayTotalUseKacl)
                    setStickGraph(tv_fri_breakfast, tv_fri_lunch, tv_fri_dinner, tv_fri_midnight_snack, dayKcalItem.mealHistoryList)
                }
                // 토
                6 -> {
                    setBottomMargin(total_use_kcal_saturday, dayKcalItem.dayTotalUseKacl)
                    setStickGraph(tv_satur_breakfast, tv_satur_lunch, tv_satur_dinner, tv_satur_midnight_snack, dayKcalItem.mealHistoryList)
                }
            }
            index++
        }
    }

    /**
     * @param iv : 막대 그래프
     * @param kcal : 해당하는 칼로리
     *  총 사이즈 218dp
     *  3000kcal 기준
     *  218 : 3000 으로 기준
     *  1dp : 13kcal의 비율로 설정
     *  dp값 = kcal / 13
     * ImageView에 적합한 20
     */
    private fun setHeight(iv: ImageView, kcal: Int) {
        if (iv.layoutParams is ViewGroup.LayoutParams) {
            val params: ViewGroup.LayoutParams = iv.layoutParams
            val dpValue = kcal / 13
            val height = (dpValue * d).toInt() // margin in pixels
            params.height = height
            iv.layoutParams = params
        }
    }

    /**
     * @param iv : 점 그래프
     * @param kcal : 해당하는 칼로리
     *  총 사이즈 218dp
     *  3000kcal 기준
     *  218 : 3000 으로 기준
     *  1dp : 13kcal의 비율로 설정
     *  dp값 = kcal / 13
     * ImageView에 적합한 20
     */
    private fun setBottomMargin(iv: ImageView, kcal: Int) {
        if (iv.layoutParams is ViewGroup.MarginLayoutParams) {
            val params: ViewGroup.MarginLayoutParams = iv.layoutParams as ViewGroup.MarginLayoutParams
            var b = kcal / 13
            params.setMargins(0, 0, 0, b * d.toInt())
            iv.requestLayout()
        }
    }

    private fun setStickGraph(
        breakfastStick: ImageView,
        lunchStick: ImageView,
        dinnerStick: ImageView,
        yasikStick: ImageView,
        mealHistoryList: ArrayList<MealHistory>
    ) {
        for (mealHistory in mealHistoryList) {
            when (mealHistory.type) {
                "B" -> {
                    setHeight(breakfastStick, mealHistory.kcal)
                    breakfastStick.visibility = View.VISIBLE
                }
                "L" -> {
                    setHeight(lunchStick, mealHistory.kcal)
                    lunchStick.visibility = View.VISIBLE
                }
                "D" -> {
                    setHeight(dinnerStick, mealHistory.kcal)
                    dinnerStick.visibility = View.VISIBLE
                }
                "M" -> {
                    setHeight(yasikStick, mealHistory.kcal)
                    yasikStick.visibility = View.VISIBLE
                }
            }
        }
    }
}