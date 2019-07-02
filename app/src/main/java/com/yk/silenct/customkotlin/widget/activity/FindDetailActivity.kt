package com.yk.silenct.customkotlin.widget.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.mvp.contract.FindDetailContract
import com.yk.silenct.customkotlin.mvp.model.bean.HotBean
import com.yk.silenct.customkotlin.mvp.presenter.FindDetailPresenter
import com.yk.silenct.customkotlin.widget.adapter.RankAdapter
import kotlinx.android.synthetic.main.activity_find_detail.*
import java.util.regex.Pattern

class FindDetailActivity : AppCompatActivity(), FindDetailContract.View, SwipeRefreshLayout.OnRefreshListener {


    lateinit var mPresenter: FindDetailPresenter
    lateinit var mAdapter: RankAdapter
    lateinit var data: String
    var mIsRefresh: Boolean? = false
    var mList: ArrayList<HotBean.ItemListBean.DataBean>? = ArrayList()
    var mStart: Int = 10
    lateinit var name: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_find_detail)
        initToolBar()
        initView()
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        mPresenter = FindDetailPresenter(this, this)
       // mPresenter.requestData(name, "date")
    }

    /**
     * 初始化toolbar
     */
    private fun initToolBar() {
        intent.getStringExtra("name")?.let {
            name = intent.getStringExtra("name")
            toolbar.title = name
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun setData(bean: HotBean) {
        val regex = "[^0-9]"
        val pattern = Pattern.compile(regex)
        val m = pattern.matcher(bean.nextPageUrl as CharSequence)
        data = m.replaceAll("").substring(1, m.replaceAll("").length - 1).toString()
        if (mIsRefresh!!) {
            mIsRefresh = false
            srl_find_detail.isRefreshing = false
            if (mList?.size!! > 0) {
                mList!!.clear()
            }
            bean.itemList.forEach { it.data.let { it1 -> mList?.add(it1) } }
        }
        initAdapter()
    }

    override fun onRefresh() {
        if (mIsRefresh!!) {
            mIsRefresh = true
            mPresenter.requestData(name, "date")
        }
    }

    /**
     * 初始化适配器
     */
    private fun initAdapter() {
        if (mList?.size!! > 0) {
            rlv_find_detail.layoutManager = LinearLayoutManager(this)
            mAdapter = RankAdapter(this, mList!!)
            rlv_find_detail.adapter = mAdapter
            srl_find_detail.setOnRefreshListener(this)
            rlv_find_detail.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    var layoutManager: LinearLayoutManager = rlv_find_detail.layoutManager as LinearLayoutManager
                    var lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == mList!!.size - 1) {
                        if (data != null) {
                            mPresenter.requestMoreData(mStart, name, "date")
                            mStart = mStart.plus(10)
                        }
                    }
                }
            })
            mAdapter.notifyDataSetChanged()
        }

    }
}
