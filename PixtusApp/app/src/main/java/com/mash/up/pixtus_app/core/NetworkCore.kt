package com.mash.up.pixtus_app.core

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


// init 키워드 , object 키워드, 빌더 패턴, 레트로핏 생성 방법
object NetworkCore {
    val api: Retrofit
    val BASE_URL = "http://ec2-13-125-35-79.ap-northeast-2.compute.amazonaws.com:8080"

    init {
        var okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    inline fun <reified T> getNetworkCore()  = api.create(T::class.java)
}

class AuthInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request().newBuilder().addHeader("Authorization", "1").build()
        return chain.proceed(request)
    }
}