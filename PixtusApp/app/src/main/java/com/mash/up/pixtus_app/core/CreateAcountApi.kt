package com.mash.up.pixtus_app.core

import com.mash.up.pixtus_app.ui.create.data.CreateAcoountRequest
import com.mash.up.pixtus_app.ui.login.PostLoginRequest
import com.mash.up.pixtus_app.ui.login.data.PostLoginResponse
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


/**
 * Created by TakHyeongMin on 2019-08-23.
 */

interface CreateAcountApi{

    @POST("/user/sign-up") // 리턴타입
    fun postCreateAccount(
        @Header("Content-Type") contentType: String,
        @Body body: CreateAcoountRequest
    ) : Completable

}