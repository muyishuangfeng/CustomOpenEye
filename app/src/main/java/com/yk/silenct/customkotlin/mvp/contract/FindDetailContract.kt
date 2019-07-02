package com.yk.silenct.customkotlin.mvp.contract

import com.yk.silenct.customkotlin.base.BasePresenter
import com.yk.silenct.customkotlin.base.BaseView
import com.yk.silenct.customkotlin.mvp.model.bean.HotBean

interface FindDetailContract {

    interface View :BaseView<Presenter>{
        fun setData(bean:HotBean)
    }


    interface Presenter:BasePresenter{
        fun requestData(categoryName:String,strategy:String)
    }
}