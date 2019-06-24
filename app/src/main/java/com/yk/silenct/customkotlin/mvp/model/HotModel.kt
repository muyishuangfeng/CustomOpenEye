package com.yk.silenct.customkotlin.mvp.model

import android.content.Context
import com.yk.silenct.customkotlin.mvp.model.bean.HotBean
import com.yk.silenct.customkotlin.net.ApiService
import com.yk.silenct.customkotlin.net.RetrofitClient
import io.reactivex.Observable

class HotModel {

    fun loadData(context: Context,strategy:String): Observable<HotBean>? {
        //获取网络客户端
        val retrofitClient=RetrofitClient.getInstance(context,ApiService.BASE_URL)
        val apiService=retrofitClient.create(ApiService::class.java)
        return apiService?.getHotData(10,strategy,"26868b32e808498db32fd51fb422d00175e179df",83)

    }
}