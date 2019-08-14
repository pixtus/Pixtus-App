package com.mash.up.pixtus_app.core

import com.mash.up.pixtus_app.MainResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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


    @GET("/main")
    fun getMain(
        @Header("Authorization") authorization: String
    ) : Single<MainResponse>
}

data class Excercises(
    var exerciseId : Int,
    var name : String,
    var kcal : Int
)