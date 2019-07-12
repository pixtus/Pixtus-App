package com.mash.up.pixtus_app.core

import com.mash.up.pixtus_app.WorkOut
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface PixtusApi {
    //TODO api 정리

  /*  {
        "mealCnt": 2,
        "uid": "abcd1234567"
    }*/

    //Any == object
    @POST("/meal")
    fun sendMeal(@Body body: HashMap<String,Any>) : Completable


    @GET("/exercises") // 리턴타입
    fun getExcercises() : Single<List<Excercises>>

    @GET("/main?uid=123")
    fun getMain() : Single<Main>
}

data class Excercises(
    var exerciseId : Int,
    var name : String,
    var kcal : Int
)

data class Main(
    var characterName : String,
    var exp : Int,
    var nextExp : Int,
    var date : String,
    var workouts: List<WorkOuts>
)

data class WorkOuts(
    var exerciseName: String,
    var totalKcal: Int
)