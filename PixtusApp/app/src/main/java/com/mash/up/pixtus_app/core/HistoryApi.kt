package com.mash.up.pixtus_app.core

import com.mash.up.pixtus_app.ui.history.data.HistoryResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HistoryApi {

    @GET("/history")
    fun getHistory(
        @Header("Authorization") authorization: String,
//        // history/{prevWeek}
//        @Path ("prevWeek") prevWeek: Int?,

        // history/?prevWeek=2
        @Query("prevWeek") prevWeek: Int?
    ): Single<HistoryResponse>

}

