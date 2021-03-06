package com.yk.silenct.customkotlin.net

import android.content.Context
import android.util.Log
import com.yk.silenct.customkotlin.util.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor(context: Context) : Interceptor {

    val context = context
    override fun intercept(chain: Interceptor.Chain): Response? {
        var request = chain.request()
        if (NetworkUtils.isNetConnect(context)) {
            val response = chain.proceed(request)
            val maxAge = 60
            val cacheControl = request.cacheControl().toString()
            Log.e("CacheInterceptor", "6s load cahe" + cacheControl)
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build()

        }else{
            Log.e("CacheInterceptor", " no network load cahe")
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            val response = chain.proceed(request)
            //set cahe times is 3 days
            val maxStale = 60 * 60 * 24 * 3
            return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build()
        }
    }

}

