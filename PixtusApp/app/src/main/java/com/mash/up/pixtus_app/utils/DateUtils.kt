package com.mash.up.pixtus_app.utils

import android.text.format.DateUtils
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*






/**
 * Created by TakHyeongMin on 2019-08-20.
 */
class DateUtils {

    fun getWeek() : String{

//        val sundayCalendar = Calendar.getInstance()
//        val saturdayCalendar = Calendar.getInstance()

        var cal = Calendar.getInstance(SimpleTimeZone(0x1ee6280, "KST"))
        var weekago = cal.time
        val fomatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        cal.clear()
        return fomatter.format(weekago)
//        var y= Integer.parseInt(fomatter.format(weekago).substring(0,4))
//        var m = Integer.parseInt(fomatter.format(weekago).substring(4,6))
//        var d = Integer.parseInt(fomatter.format(weekago).substring(6))


        /*sundayCalendar.clear()
        saturdayCalendar.clear()
        sundayCalendar.set(Calendar.YEAR, y)
        sundayCalendar.set(Calendar.MONTH,m)
        sundayCalendar.set(Calendar.DATE,d)
        sundayCalendar.set(Calendar.WEEK_OF_MONTH, cal.get(Calendar.WEEK_OF_MONTH))
        sundayCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)


        saturdayCalendar.set(Calendar.YEAR, y)
        saturdayCalendar.set(Calendar.MONTH,m)
        saturdayCalendar.set(Calendar.DATE,d)
        saturdayCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)

        Log.e("ㄹㄹㄹㄹㄹ", fomatter.format(sundayCalendar.time))

        Log.e("ㄹㄹㄹㄹㄹ", fomatter.format(saturdayCalendar.time))*/

        // val sundayToSaturdayString = sundayCalendar.get(Calendar.YEAR).toString() + "년 " + (sundayCalendar.get(Calendar.MONTH)).toString() + "월 " + sundayCalendar.get(Calendar.DATE).toString() + "일 - " + saturdayCalendar.get(Calendar.DATE).toString() + "일"

//        val sundayToSaturdayString = cal.get(Calendar.YEAR).toString() + " 년 " + (cal.get(Calendar.MONTH) + 1).toString() + " 월 " + cal.get(Calendar.WEEK_OF_MONTH) + " 주 "
//
//        return sundayToSaturdayString
    }

//    fun get7DayAgo(preWeek: Int): String? {
//        var cal = Calendar.getInstance(SimpleTimeZone(0x1ee6280, "KST"))
//        cal.add(Calendar.DATE, -7 * preWeek)
//        var weekago = cal.time
//        val fomatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
//        return fomatter.format(weekago)
//    }

    fun get7DayAgoDate(year: Int,month : Int,day : Int) : String{
        var cal = Calendar.getInstance()
        cal.set(year, month -1, day)
        cal.add(Calendar.DATE, -7)

        var weekago = cal.getTime()
        var formatter = SimpleDateFormat("yyyyMMdd")
        Locale.getDefault()
        return formatter.format(weekago)
    }

    fun get7DayGoDate(year: Int,month : Int,day : Int) : String{
        var cal = Calendar.getInstance()
        cal.set(year, month -1, day)
        cal.add(Calendar.DATE,+7)

        var weekago = cal.getTime()
        var formatter = SimpleDateFormat("yyyyMMdd")
        Locale.getDefault()
        return formatter.format(weekago)
    }

    fun getDayOfWeek(dateStr: String): String {
        val formatter = SimpleDateFormat("yyyyMMdd")

        var date = formatter.parse(dateStr)  // 날짜 입력하는곳 .
        date = Date(date.getTime() + 1000 * 60 * 60 * 24)  // 날짜에 하루를 더한 값
        // 하루를 뺄려면 (1000*60*60*24*-1) 해주시면 됩니다.

        val cal = Calendar.getInstance()
        cal.setTime(date)              // 하루더한 날자 값을 Calendar  넣는다.

        val dayNum = cal.get(Calendar.DAY_OF_WEEK)   // 요일을 구해온다.

        when (dayNum) {

            1 -> return "토"
            2 -> return "일"
            3 -> return "월"
            4 -> return "화"
            5 -> return "수"
            6 -> return "목"
            7 -> return "금"

        }
        return "wrong value"
    }

    fun getSunday(y : String, m : String, d : String): String{
        var fomatter= SimpleDateFormat("yyyyMMdd")
        var c = Calendar.getInstance()

        var y=Integer.parseInt(y)

        var m=Integer.parseInt(m)-1

        var d=Integer.parseInt(d)

        c.set(Calendar.YEAR, y)
        c.set(Calendar.MONTH, m)
        c.set(Calendar.DATE, d)
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        return fomatter.format(c.time)
    }

    fun getSaturday(y : String, m : String, d : String): String{
        var fomatter= SimpleDateFormat("yyyyMMdd")
        var c = Calendar.getInstance()

        var y=Integer.parseInt(y)

        var m=Integer.parseInt(m)-1

        var d=Integer.parseInt(d)

        c.set(Calendar.YEAR, y)
        c.set(Calendar.MONTH, m)
        c.set(Calendar.DATE, d)
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        return fomatter.format(c.time)
    }
}