package com.yk.silenct.customkotlin.mvp.model

import android.content.Context
import com.yk.silenct.customkotlin.mvp.model.bean.HotBean
import com.yk.silenct.customkotlin.net.ApiService
import com.yk.silenct.customkotlin.net.RetrofitClient
import io.reactivex.Observable

class FindDetailModel {

    /**
     * 加载数据
     */
    fun loadData(context: Context, categoryName: String, strategy: String): Observable<HotBean>? {
        return RetrofitClient.getInstance(context, ApiService.BASE_URL)
                .create(ApiService::class.java)
                ?.getFindDetailData(categoryName, strategy, "26868b32e808498db32fd51fb422d00175e179df", 83)

    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(context: Context, start: Int,categoryName: String, strategy: String): Observable<HotBean>? {
        return RetrofitClient.getInstance(context, ApiService.BASE_URL)
                .create(ApiService::class.java)
                ?.getFindDetailMoreData(start, 10, categoryName, strategy)
    }
}