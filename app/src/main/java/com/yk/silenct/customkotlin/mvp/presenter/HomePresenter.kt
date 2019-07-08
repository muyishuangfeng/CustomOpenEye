package com.yk.silenct.customkotlin.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.yk.silenct.customkotlin.mvp.contract.HomeContract
import com.yk.silenct.customkotlin.mvp.model.HomeModel
import com.yk.silenct.customkotlin.mvp.model.bean.HomeBean
import com.yk.silenct.customkotlin.util.applySchedulers
import io.reactivex.Observable

class HomePresenter(context: Context, view: HomeContract.View) : HomeContract.Presenter {

    var mView: HomeContract.View? = null
    var mContext: Context? = null
    val mModel: HomeModel by lazy {
        HomeModel()
    }

    init {
        mContext = context
        mView = view
    }

    override fun start() {
        requestData()
    }


    @SuppressLint("CheckResult")
    override fun requestData() {
        val observable: Observable<HomeBean>? = mContext?.let { mModel.loadData(it, true, "0") }
        observable?.applySchedulers()?.subscribe { homeBean: HomeBean -> mView?.setData(homeBean) }
    }

    @SuppressLint("CheckResult")
    fun moreData(data: String?) {
        val observable: Observable<HomeBean>? = mContext?.let { mModel.loadData(it, false, data!!) }
        observable?.applySchedulers()?.subscribe { homeBean: HomeBean -> mView?.setData(homeBean) }
    }

}
