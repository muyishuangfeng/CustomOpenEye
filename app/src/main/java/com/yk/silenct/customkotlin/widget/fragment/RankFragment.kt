package com.yk.silenct.customkotlin.widget.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.base.BaseFragment
import com.yk.silenct.customkotlin.mvp.contract.HotContract
import com.yk.silenct.customkotlin.mvp.model.bean.HotBean
import com.yk.silenct.customkotlin.mvp.presenter.HotPresenter
import com.yk.silenct.customkotlin.widget.adapter.RankAdapter
import kotlinx.android.synthetic.main.fragment_rank.*

class RankFragment : BaseFragment(), HotContract.View {

    lateinit var mPresenter: HotPresenter
    lateinit var mStrategy: String
    lateinit var mAdapter: RankAdapter
    var mList: ArrayList<HotBean.ItemListBean.DataBean> = ArrayList()


    override fun getLayoutID(): Int {
        return R.layout.fragment_rank
    }

    override fun initView() {
        rlv_rank.layoutManager = LinearLayoutManager(context)
        mAdapter = RankAdapter(context!!, mList)
        rlv_rank.adapter = mAdapter
        if (arguments != null) {
            mStrategy = arguments!!.getString("strategy")
            mPresenter = HotPresenter(context!!, this)
            mPresenter.requestData(mStrategy)
        }

    }

    override fun setData(bean: HotBean) {
        if (mList.size > 0) {
            mList.clear()
        }
        bean.itemList.forEach {
            it.data.let { it1 -> mList.add(it1) }
        }
        mAdapter.notifyDataSetChanged()
    }

}