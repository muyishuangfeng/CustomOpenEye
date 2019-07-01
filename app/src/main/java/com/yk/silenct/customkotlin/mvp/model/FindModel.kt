package com.yk.silenct.customkotlin.mvp.model

import android.content.Context
import com.yk.silenct.customkotlin.mvp.model.bean.FindBean
import com.yk.silenct.customkotlin.net.ApiService
import com.yk.silenct.customkotlin.net.RetrofitClient
import io.reactivex.Observable

class FindModel{
    /**
     * 加载数据
     */
    fun loadData(context: Context):Observable<MutableList<FindBean>>?{
        var retrofitClient=RetrofitClient.getInstance(context,ApiService.BASE_URL)
        var apiService=retrofitClient.create(ApiService::class.java)
        return apiService?.getFindData()
    }
}