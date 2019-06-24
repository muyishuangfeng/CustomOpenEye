package com.yk.silenct.customkotlin.net

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.Exception
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor(context: Context, baseUrl: String) {

    var httpCatchDirectroy: File? = null
    //上下文
    var mContext: Context = context
    //缓存
    var cache: Cache? = null
    //okhttp
    var okHttpClient: OkHttpClient? = null
    //retofit
    var retrofit: Retrofit? = null
    //url
    var url: String = baseUrl
    //超时
    val DEFAULT_TIMEOUT: Long = 20
    val CACHE_NAME: String = "app_cache"


    init {
        //缓存地址
        if (httpCatchDirectroy == null) {
            httpCatchDirectroy = File(mContext.cacheDir, CACHE_NAME)
        }
        try {
            if (cache == null) {
                cache = Cache(httpCatchDirectroy!!, 10 * 1024 * 1024)
            }
        } catch (e: Exception) {
            Log.e("OKHttp", "Could not create http cache", e)
        }
        //okhttp创建
        okHttpClient = OkHttpClient.Builder()
                //日志
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)
                .addInterceptor(CacheInterceptor(context))
                .addNetworkInterceptor(CacheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build()

        //retrofit创建
        retrofit = Retrofit.Builder()
                .client(okHttpClient!!)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        var sInstances: RetrofitClient? = null

        fun getInstance(context: Context, baseUrl: String): RetrofitClient {
            if (sInstances == null) {
                synchronized(RetrofitClient::class) {
                    if (sInstances == null) {
                        sInstances = RetrofitClient(context, baseUrl)
                    }
                }
            }
            return sInstances!!
        }
    }

    fun <T> create(service: Class<T>?): T? {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit?.create(service)
    }

}