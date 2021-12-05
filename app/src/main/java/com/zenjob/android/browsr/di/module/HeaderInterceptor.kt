package com.zenjob.android.browsr.di.module

import android.util.Log
import com.zenjob.android.browsr.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.net.ssl.SSLHandshakeException


open class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder().header("api_key", BuildConfig.TMDB_API_KEY)
        val newRequest = requestBuilder.build()
        Log.d("request","new request: " + newRequest.url.toString())

        return try {
            chain.proceed(newRequest)
        } catch (e: SSLHandshakeException) {
            //retry
            chain.proceed(newRequest)
        }
    }
}