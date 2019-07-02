package com.yk.silenct.customkotlin.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.yk.silenct.customkotlin.mvp.contract.FindDetailContract
import com.yk.silenct.customkotlin.mvp.model.FindDetailModel
import com.yk.silenct.customkotlin.mvp.model.bean.HotBean
import com.yk.silenct.customkotlin.util.applySchedulers
import io.reactivex.Observable

class FindDetailPresenter(context: Context, view: FindDetailContract.View) : FindDetailContract.Presenter {

    var mContext: Context? = null
    var mView: FindDetailContract.View? = null
    val mModel: FindDetailModel by lazy {
        FindDetailModel()
    }

    init {
        mContext = context
        mView = view

    }

    override fun start() {
    }

    /**
     * 加载数据
     */
    @SuppressLint("CheckResult")
    override fun requestData(categoryName: String, strategy: String) {
        val observable: Observable<HotBean>? = mContext?.let { mModel.loadData(mContext!!, categoryName, strategy) }
        observable?.applySchedulers()?.subscribe { bean: HotBean -> mView?.setData(bean) }
    }

    /**
     * 加载更多数据
     */
    @SuppressLint("CheckResult")
    fun requestMoreData(start: Int, categoryName: String, strategy: String) {
        val observable: Observable<HotBean>? = mContext?.let {
            mModel.loadMoreData(mContext!!, start,
                    categoryName, strategy)
        }
        observable?.applySchedulers()?.subscribe { bean: HotBean -> mView?.setData(bean) }
    }


}