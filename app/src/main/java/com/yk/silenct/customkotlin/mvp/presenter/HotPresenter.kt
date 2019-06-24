package com.yk.silenct.customkotlin.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.yk.silenct.customkotlin.mvp.contract.HotContract
import com.yk.silenct.customkotlin.mvp.model.HotModel
import com.yk.silenct.customkotlin.mvp.model.bean.HotBean
import com.yk.silenct.customkotlin.util.applySchedulers
import io.reactivex.Observable

class HotPresenter(context: Context, view: HotContract.View) : HotContract.Presenter {

    var mContext: Context? = null;
    var mView: HotContract.View? = null
    val mModel: HotModel by lazy {
        HotModel()
    }

    init {
        mContext = context
        mView = view
    }

    @SuppressLint("CheckResult")
    override fun requestData(strategy: String) {
        val observable: Observable<HotBean>? = mContext.let { mModel.loadData(mContext!!, strategy) }
        observable?.applySchedulers()?.subscribe { bean: HotBean ->
            mView?.setData(bean)
        }
    }

    override fun start() {
    }


}