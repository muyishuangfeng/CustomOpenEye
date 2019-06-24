package com.yk.silenct.customkotlin.widget.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.base.BaseFragment
import com.yk.silenct.customkotlin.widget.adapter.HotAdapter
import kotlinx.android.synthetic.main.fragment_hot.*

class HotFragment : BaseFragment() {

    var mTabs = listOf<String>("周排行", "月排行", "总排行").toMutableList()
    lateinit var mFragments: ArrayList<Fragment>
    val STRATEGE = arrayListOf<String>("weekly", "monthly", "historical")


    override fun getLayoutID(): Int {
        return R.layout.fragment_hot
    }

    override fun initView() {
        //周排行
        var weekFragment: RankFragment = RankFragment()
        var weekBundle = Bundle()
        weekBundle.putString("strategy", STRATEGE[0])
        weekFragment.arguments = weekBundle
        //月排行
        var monthFragment: RankFragment = RankFragment()
        var monthBundle = Bundle()
        monthBundle.putString("strategy", STRATEGE[1])
        monthFragment.arguments = monthBundle
        //总排行
        var allFragment: RankFragment = RankFragment()
        var allBundle = Bundle()
        allBundle.putString("strategy", STRATEGE[2])
        allFragment.arguments = allBundle

        mFragments = ArrayList()
        mFragments.add(weekFragment as Fragment)
        mFragments.add(monthFragment as Fragment)
        mFragments.add(allFragment as Fragment)
        vpg_hot.adapter = HotAdapter(fragmentManager, mFragments, mTabs)
        tab_hot.setupWithViewPager(vpg_hot)


    }

}