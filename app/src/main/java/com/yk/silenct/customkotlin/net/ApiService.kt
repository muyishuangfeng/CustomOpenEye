package com.yk.silenct.customkotlin.net

import com.yk.silenct.customkotlin.bean.HomeBean
import com.yk.silenct.customkotlin.mvp.model.bean.FindBean
import com.yk.silenct.customkotlin.mvp.model.bean.HotBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        val BASE_URL: String
            get() = "http://baobab.kaiyanapp.com/api/"
    }

    //获取首页第一页数据
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getHomeData(): Observable<HomeBean>

    @GET("v3/ranklist")
    fun getHotData(@Query("num") num: Int, @Query("strategy") strategy: String,
                   @Query("udid") udid: String, @Query("vc") vc: Int): Observable<HotBean>

    /**
     * 发现频道信息
     */
    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getFindData(): Observable<MutableList<FindBean>>


    //获取发现频道详情信息
    @GET("v3/videos")
    fun getFindDetailData(@Query("categoryName") categoryName: String,
                          @Query("strategy") strategy: String,
                          @Query("uidi") udid: String,
                          @Query("vc") vc: Int): Observable<HotBean>

    //获取发现详情加载更多消息
    @GET("v3/videos")
    fun getFindDetailMoreData(@Query("start") start: Int,
                              @Query("num") num: Int,
                              @Query("categoryName") categoryName: String,
                              @Query("strategy") strategy: String): Observable<HotBean>

    //获取关键词搜索相关信息
    @GET("v1/search")
    fun getSearchData(@Query("num") num: Int,
                      @Query("query") query: Int,
                      @Query("start") start: Int): Observable<HotBean>
}