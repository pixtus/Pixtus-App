package com.mash.up.pixtus_app.core


import com.mash.up.pixtus_app.data.Exercise
import com.mash.up.pixtus_app.data.StepData
import com.mash.up.pixtus_app.Meal
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
    fun postMeal(
        @Header("Authorization") authorization: String,
        @Body body: Meal) : Completable

    @POST("/workout")
    fun sendStep(
        @Header("Authorization") authorization: String,
        @Body body: StepData
    ) : Completable
    /*
    * {
    * "amount": 0,
  "exerciseId": 0

}
    * */


    @GET("/exercises") // 리턴타입
    fun getExcercises(
        @Header("Authorization") authorization: String
    ) : Single<List<Exercise>>

    @GET("/main")
    fun getMain(
        @Header("Authorization") authorization: String
    ) : Single<MainResponse>
}
