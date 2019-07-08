package com.yk.silenct.customkotlin.widget.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.base.BaseFragment
import com.yk.silenct.customkotlin.mvp.contract.HomeContract
import com.yk.silenct.customkotlin.mvp.model.bean.HomeBean
import com.yk.silenct.customkotlin.mvp.presenter.HomePresenter
import com.yk.silenct.customkotlin.widget.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.regex.Pattern

class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    var mIsRefresh: Boolean? = false
    var mPresenter: HomePresenter? = null
    var mList = ArrayList<HomeBean.IssueItemBean.ItemListBean>()
    var data: String? = null
    var mAdapter: HomeAdapter? = null


    override fun getLayoutID(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        mPresenter = HomePresenter(context!!, this)
        mPresenter?.start()
        srl_home.setOnRefreshListener(this)

    }


    /**
     * 设置数据
     */
    override fun setData(bean: HomeBean) {
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(bean.nextPageUrl)
        data = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (mIsRefresh!!) {
            mIsRefresh = false
            srl_home.isRefreshing = false
            if (mList.size > 0) {
                mList.clear()
            }

        }
        bean.issueList
                .flatMap { it.itemList }
                .filter { it.type.equals("video") }
                .forEach { mList.add(it) }
        initAdapter()
    }

    override fun onRefresh() {
        if (!mIsRefresh!!) {
            mIsRefresh = true
            mPresenter?.start()
        }
    }

    fun initAdapter() {
        if (mList.size > 0) {
            rlv_home.layoutManager = LinearLayoutManager(context)
            mAdapter = HomeAdapter(context!!, mList)
            rlv_home.adapter = mAdapter
            rlv_home.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    var layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    var lastPositon = layoutManager.findLastVisibleItemPosition()
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size - 1) {
                        if (data != null) {
                            mPresenter?.moreData(data)
                        }

                    }
                }
            })
            mAdapter?.notifyDataSetChanged()
        }

    }
}