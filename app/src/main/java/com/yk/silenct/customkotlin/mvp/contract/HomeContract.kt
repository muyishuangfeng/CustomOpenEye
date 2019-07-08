package com.yk.silenct.customkotlin.mvp.contract

import com.yk.silenct.customkotlin.base.BasePresenter
import com.yk.silenct.customkotlin.base.BaseView
import com.yk.silenct.customkotlin.mvp.model.bean.HomeBean

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun setData(bean:HomeBean)
    }

    interface Presenter : BasePresenter {
        fun requestData()
    }
}