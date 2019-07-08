package com.yk.silenct.customkotlin.widget.fragment

import android.content.Intent
import android.view.View
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.base.BaseFragment
import com.yk.silenct.customkotlin.widget.activity.AdviseActivity
import com.yk.silenct.customkotlin.widget.activity.CacheActivity
import com.yk.silenct.customkotlin.widget.activity.WatchActivity
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment(), View.OnClickListener {

    override fun getLayoutID(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        tv_advise.setOnClickListener(this)
        tv_watch.setOnClickListener(this)
        tv_save.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_watch -> {
                startActivity(Intent(activity, WatchActivity::class.java))
            }
            R.id.tv_advise -> {
                startActivity(Intent(activity, AdviseActivity::class.java))
            }
            R.id.tv_save -> {
                startActivity(Intent(activity, CacheActivity::class.java))
            }
        }
    }
}