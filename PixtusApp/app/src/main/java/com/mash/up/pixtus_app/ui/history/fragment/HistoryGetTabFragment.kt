package com.mash.up.pixtus_app.ui.history.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.core.HistoryApi
import com.mash.up.pixtus_app.core.NetworkCore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_history_get_tab.*
import com.mash.up.pixtus_app.ui.history.data.HistoryResponse
import com.mash.up.pixtus_app.ui.history.data.MealHistory
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by TakHyeongMin on 2019-07-25.
 */
class HistoryGetTabFragment : Fragment() {

    private var prevWeek: Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_history_get_tab, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        getHistory(2)
    }

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
            }, {
                // 실패했을 때
                Log.d("list_data", Log.getStackTraceString(it))
            })
    }

    private fun getDayOfWeek(date: String): String {
        val formatter = SimpleDateFormat("yyyyMMdd")

        var date = formatter.parse(date)  // 날짜 입력하는곳 .
        date = Date(date.getTime() + 1000 * 60 * 60 * 24 * +1)  // 날짜에 하루를 더한 값
        // 하루를 뺄려면 (1000*60*60*24*-1) 해주시면 됩니다.

        val cal = Calendar.getInstance()
        cal.setTime(date)              // 하루더한 날자 값을 Calendar  넣는다.

        val dayNum = cal.get(Calendar.DAY_OF_WEEK)   // 요일을 구해온다.

        var convertedString = ""

        when (dayNum) {
            1 -> convertedString = "일요일"
            2 -> convertedString = "월요일"
            3 -> convertedString = "화요일"
            4 -> convertedString = "수요일"
            5 -> convertedString = "목요일"
            6 -> convertedString = "금요일"
            7 -> convertedString = "토요일"
        }

        return convertedString
    }

    private fun configureGraph(historyResponse: HistoryResponse){
        for(data in historyResponse.mealHistory){

        }
    }

    private fun configureWeekGraph(dayOfWeek: String, historyResponse: HistoryResponse){
        when(dayOfWeek){
            "일요일" -> {

            }
            "월요일" -> {

            }
            "화요일" -> {

            }
            "수요일" -> {

            }
            "목요일" -> {

            }
            "금요일" -> {

            }
            "토요일" -> {

            }
        }

    }

    private fun setOnClickListener() {
        btn_left.setOnClickListener {
            incresePrevWeek()
        }

        btn_right.setOnClickListener {
            decreasePrevWeek()
        }
    }


    private fun incresePrevWeek() {
        prevWeek++
    }

    private fun decreasePrevWeek() {
        if (prevWeek > 0)
            prevWeek--
    }
}