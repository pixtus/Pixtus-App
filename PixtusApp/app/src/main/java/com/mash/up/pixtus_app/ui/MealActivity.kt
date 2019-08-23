package com.mash.up.pixtus_app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.mash.up.pixtus_app.Meal
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import com.mash.up.pixtus_app.utils.showToastMessageString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_meal.*
import kotlinx.android.synthetic.main.activity_meal.view.*
import java.text.SimpleDateFormat
import java.util.*

class MealActivity : BaseActivity() {
    var mealCnt = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        var dateFormat = SimpleDateFormat("MM월 dd일")
        tv_meal1.text = dateFormat.format(Date()).toString()

        layout_breakfast.isSelected = false
        layout_lunch.isSelected = false
        layout_dinner.isSelected = false
        layout_nightmeal.isSelected = false
        layout_next.isSelected = validateStep()

        layout_next.setOnClickListener {
            if (layout_next.isSelected) {
                val animationView = findViewById<LottieAnimationView>(R.id.lottie_meal)

                animationView.setAnimation("ic_check.json")
                animationView.playAnimation()
                meal_blur.visibility = View.VISIBLE

                var meal = Meal(layout_breakfast.isSelected,layout_lunch.isSelected, layout_dinner.isSelected, layout_nightmeal.isSelected)

                NetworkCore.getNetworkCore<PixtusApi>()
                    .postMeal(
                        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwidWlkIjoiMTIzNCJ9.KRCUrR_TqDXXfVnAxSIsQ17E8GtvOewPZCh9GOtFJVY",
                        meal
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("list_data", it.toString())
                    }, {
                        Log.d("list_data", Log.getStackTraceString(it))
                    })

                Handler().postDelayed({
                    finish()
                }, 1000)
            } else {
                showToastMessageString("식사 정보를 입력하세요.")
            }
        }

        btn_meal_back.setOnClickListener {
            finish()
        }

        layout_breakfast.setOnClickListener {
            layout_breakfast.isSelected = !layout_breakfast.isSelected
            statusNext(validateStep())
        }

        layout_lunch.setOnClickListener {
            layout_lunch.isSelected = !layout_lunch.isSelected
            statusNext(validateStep())
        }

        layout_dinner.setOnClickListener {
            layout_dinner.isSelected = !layout_dinner.isSelected
            statusNext(validateStep())
        }

        layout_nightmeal.setOnClickListener {
            layout_nightmeal.isSelected = !layout_nightmeal.isSelected
            statusNext(validateStep())
        }
    }

    fun validateStep(): Boolean {
        mealCnt = 0
        var return_bool = false
        if (layout_breakfast.isSelected)
            mealCnt++
        if (layout_lunch.isSelected)
            mealCnt++
        if (layout_dinner.isSelected)
            mealCnt++
        if (layout_nightmeal.isSelected)
            mealCnt++
        if (mealCnt > 0)
            return_bool = true
        return return_bool
    }

    fun statusNext(boolean: Boolean) {
        layout_next.isSelected = boolean
    }
}