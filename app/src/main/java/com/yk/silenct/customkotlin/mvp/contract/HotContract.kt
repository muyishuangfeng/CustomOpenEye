package com.yk.silenct.customkotlin.mvp.contract

import com.yk.silenct.customkotlin.base.BasePresenter
import com.yk.silenct.customkotlin.base.BaseView
import com.yk.silenct.customkotlin.bean.HomeBean
import com.yk.silenct.customkotlin.mvp.model.bean.HotBean

interface HotContract {
    /**
     * 设置数据
     */
    interface View : BaseView<Presenter> {
        fun setData(bean: HotBean)
    }

    /**
     * 请求数据
     */
    interface Presenter : BasePresenter {
        fun requestData(strategy: String)
    }
}