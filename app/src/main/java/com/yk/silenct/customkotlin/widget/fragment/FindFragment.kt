package com.yk.silenct.customkotlin.widget.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.base.BaseFragment
import com.yk.silenct.customkotlin.impl.OnItemClickListener
import com.yk.silenct.customkotlin.mvp.contract.FindContract
import com.yk.silenct.customkotlin.mvp.model.bean.FindBean
import com.yk.silenct.customkotlin.mvp.presenter.FindPresenter
import com.yk.silenct.customkotlin.widget.activity.FindDetailActivity
import com.yk.silenct.customkotlin.widget.adapter.FindAdapter
import kotlinx.android.synthetic.main.fragment_find.*


class FindFragment : BaseFragment(), FindContract.View, OnItemClickListener {


    var mAdapter: FindAdapter? = null
    var mList: MutableList<FindBean>? = null
    var mPresenter: FindPresenter? = null


    override fun getLayoutID(): Int {
        return R.layout.fragment_find
    }

    override fun initView() {
        mPresenter = FindPresenter(context!!, this)
        mPresenter?.start()


    }

    override fun setData(beans: MutableList<FindBean>) {
        mAdapter?.mList = beans
        mList = beans
        if (mList!=null){
            rlv_find.layoutManager = GridLayoutManager(context,2)
            mAdapter = FindAdapter(context!!, mList!!)
            mAdapter?.setOnItemClickListener(this)
            rlv_find.adapter = mAdapter
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun onItemClick(itemView: View, position: Int) {
        var bean = mList?.get(position)
        var name = bean?.name
        var intent: Intent = Intent(context, FindDetailActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }

}
