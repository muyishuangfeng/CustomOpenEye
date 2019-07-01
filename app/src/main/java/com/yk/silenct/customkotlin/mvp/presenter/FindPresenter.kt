package com.yk.silenct.customkotlin.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.yk.silenct.customkotlin.mvp.contract.FindContract
import com.yk.silenct.customkotlin.mvp.model.FindModel
import com.yk.silenct.customkotlin.mvp.model.bean.FindBean
import com.yk.silenct.customkotlin.util.applySchedulers
import io.reactivex.Observable

class FindPresenter(context: Context, view: FindContract.View) : FindContract.Presenter {

    var mContext: Context? = null
    var mView: FindContract.View? = null
    val mModel: FindModel by lazy {
        FindModel()
    }

    init {
        this.mContext = context
        this.mView = view
    }

    override fun start() {
        requestData()
    }

    @SuppressLint("CheckResult")
    override fun requestData() {
        val observable: Observable<MutableList<FindBean>>? = mContext?.let { mModel.loadData(mContext!!) }
        observable?.applySchedulers()?.subscribe { beans: MutableList<FindBean> ->
            mView?.setData(beans)
        }
    }

}