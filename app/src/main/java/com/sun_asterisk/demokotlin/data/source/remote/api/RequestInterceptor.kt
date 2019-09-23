package com.sun_asterisk.demokotlin.data.source.remote.api

import com.sun_asterisk.demokotlin.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().let {
            it.newBuilder().url(
                it.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()
            ).build()
        })
    }
}
