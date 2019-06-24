package com.yk.silenct.customkotlin.widget.activity

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.widget.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var findFragment: FindFragment? = null
    var homeFragment: HomeFragment? = null
    var hotFragment: HotFragment? = null
    var mineFragment: MineFragment? = null
    var mExitTime: Long = 0
    var toast: Toast? = null
    //lateinit 只用于变量 var，而 lazy 只用于常量 val(都是为了延迟加载)
    lateinit var searchFragment: SearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).transparentBar()
                .barAlpha(0.3f)
                .fitsSystemWindows(true).init()
        val window = window
        val params = window.attributes
        //隐藏导航栏
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes = params
        initToolBar()
        initFragment(savedInstanceState)
        setRadioButton()

    }

    fun initToolBar() {
        var today = getToday()
        tv_bar_title.text = today
        tv_bar_title.typeface = Typeface.createFromAsset(this.assets, "fonts/Lobster-1.4.otf")
        iv_search.setOnClickListener {
            if (rb_mine.isChecked) {
                //TODO:
            } else {
                //todo 点击搜索
                searchFragment = SearchFragment()
                searchFragment.show(supportFragmentManager, SEARCH_TAG)
            }
        }

    }

    fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            //异常情况
            val mFragments: List<androidx.fragment.app.Fragment> = supportFragmentManager.fragments
            for (item in mFragments) {
                if (item is HomeFragment) {
                    homeFragment = item
                }
                if (item is FindFragment) {
                    findFragment = item
                }
                if (item is HotFragment) {
                    hotFragment = item
                }
                if (item is MineFragment) {
                    mineFragment = item
                }
            }
        } else {
            homeFragment = HomeFragment()
            hotFragment = HotFragment()
            mineFragment = MineFragment()
            findFragment = FindFragment()
            val fragmentTrans = supportFragmentManager.beginTransaction()
            fragmentTrans.add(R.id.fl_content, homeFragment!!)
            fragmentTrans.add(R.id.fl_content, hotFragment!!)
            fragmentTrans.add(R.id.fl_content, mineFragment!!)
            fragmentTrans.add(R.id.fl_content, findFragment!!)
            fragmentTrans.commit()
        }
        supportFragmentManager.beginTransaction()
                .show(homeFragment!!)
                .hide(hotFragment!!)
                .hide(mineFragment!!)
                .hide(findFragment!!)
                .commit()
    }


    override fun onClick(v: View?) {
        clearState()
        when (v?.id) {
            R.id.rb_find -> {//发现
                rb_find.isChecked = true
                rb_find.setTextColor(resources.getColor(R.color.colorBlack))
                supportFragmentManager.beginTransaction()
                        .show(findFragment!!)
                        .hide(homeFragment!!)
                        .hide(mineFragment!!)
                        .hide(hotFragment!!)
                        .commit()
                tv_bar_title.text = "Discover"
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
            }
            R.id.rb_home -> {//主页
                rb_home.isChecked = true
                rb_home.setTextColor(resources.getColor(R.color.colorBlack))
                supportFragmentManager.beginTransaction()
                        .show(homeFragment!!)
                        .hide(findFragment!!)
                        .hide(mineFragment!!)
                        .hide(hotFragment!!)
                        .commit()
                tv_bar_title.text = getToday()
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
            }
            R.id.rb_hot -> {//热门
                rb_hot.isChecked = true
                rb_hot.setTextColor(resources.getColor(R.color.colorBlack))
                supportFragmentManager.beginTransaction()
                        .show(hotFragment!!)
                        .hide(findFragment!!)
                        .hide(mineFragment!!)
                        .hide(homeFragment!!)
                        .commit()
                tv_bar_title.text = "Ranking"
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
            }
            R.id.rb_mine -> {//我的
                rb_mine.isChecked = true
                rb_mine.setTextColor(resources.getColor(R.color.colorBlack))
                supportFragmentManager.beginTransaction()
                        .show(mineFragment!!)
                        .hide(findFragment!!)
                        .hide(homeFragment!!)
                        .hide(hotFragment!!)
                        .commit()
                tv_bar_title.text = "Mime"
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_setting)
            }
        }

    }

    /**
     * 获取日期
     */
    private fun getToday(): String {
        var list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        var date: Date = Date()
        var calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index <= 0) {
            index = 0
        }
        return list[index]
    }

    /**
     * 底部按钮
     */
    private fun setRadioButton() {
        rb_home.isChecked = true
        rb_home.setTextColor(resources.getColor(R.color.colorBlack))
        rb_home.setOnClickListener(this)
        rb_find.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
        rb_hot.setOnClickListener(this)
    }

    /**
     * 清除状态
     */
    private fun clearState() {
        rg_root.clearCheck()
        rb_hot.setTextColor(resources.getColor(R.color.colorGray))
        rb_mine.setTextColor(resources.getColor(R.color.colorGray))
        rb_find.setTextColor(resources.getColor(R.color.colorGray))
        rb_home.setTextColor(resources.getColor(R.color.colorGray))
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 3000) {
                finish()
                toast!!.cancel()
            } else {
                mExitTime == System.currentTimeMillis()
                toast = Toast.makeText(this, "请再按一次退出", Toast.LENGTH_LONG)
                toast!!.show()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
