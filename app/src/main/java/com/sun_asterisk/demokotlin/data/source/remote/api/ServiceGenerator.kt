package com.sun_asterisk.demokotlin.data.source.remote.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sun_asterisk.demokotlin.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGenerator {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val READ_TIMEOUT = 10000
    private const val WRITE_TIMEOUT = 10000
    private const val CONNECT_TIMEOUT = 10000
    fun create(): MovieService {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)!!
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(RequestInterceptor())
            .retryOnConnectionFailure(true)
        if (BuildConfig.DEBUG){
            httpClient.addInterceptor(logging)
        }
        val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(MovieService::class.java)
    }
}