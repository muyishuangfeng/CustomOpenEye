package com.yk.silenct.customkotlin.mvp.contract

import com.yk.silenct.customkotlin.base.BasePresenter
import com.yk.silenct.customkotlin.base.BaseView
import com.yk.silenct.customkotlin.mvp.model.bean.FindBean

interface FindContract {

    interface View : BaseView<Presenter> {
        //设置数据
        fun setData(beans: MutableList<FindBean>)
    }


    interface Presenter : BasePresenter {
        //请求数据
        fun requestData()
    }
}