package com.mash.up.pixtus_app.core

import com.mash.up.pixtus_app.ui.history.data.HistoryResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


/**
 * Created by TakHyeongMin on 2019-08-23.
 */
interface HistoryApi {
    @GET("/history")
    fun getHistory(
        @Header("Authorization") authorization: String,
        @Query("prevWeek") prevWeek: Int?
    ): Single<HistoryResponse>
}