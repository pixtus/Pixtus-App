package com.mash.up.pixtus_app.core

import com.mash.up.pixtus_app.domain.Exercise
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface PixtusApi {
    //TODO api 정리


    @POST("/sign-in")
    fun loginUser(@Body body : HashMap<Any , String>) : Single<Result<Void>>

    @POST("/register")
    fun registerUser(@Body body: HashMap<Any, Any>) : Completable

    @GET("/excercises")
    fun getExercise() : Single<List<Exercise>>
}