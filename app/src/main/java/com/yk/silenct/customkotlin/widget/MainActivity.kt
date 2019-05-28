package com.yk.silenct.customkotlin.widget

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.widget.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,View.OnClickListener{

    var findFragment:FindFragment?=null
    var homeFragment:HomeFragment?=null
    var hotFragment:HotFragment?=null
    var mineFragment:MineFragment?=null
    var mExitTime:Long=0
    var toast:Toast?=null
    //lateinit 只用于变量 var，而 lazy 只用于常量 val(都是为了延迟加载)
    lateinit var searchFragment: SearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).transparentBar()
                .barAlpha(0.3f)
                .fitsSystemWindows(true).init()
        val window=window
        val params=window.attributes
        //隐藏导航栏
        params.systemUiVisibility=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes=params
        initToolBar()
        initFragment(savedInstanceState)

    }

    fun initToolBar(){
        var today=getToday()
        tv_bar_title.text=today
        tv_bar_title.typeface= Typeface.createFromAsset(this.assets,"fonts/Lobster-1.4.otf")
        iv_search.setOnClickListener{
            if (rb_mine.isChecked){
                //TODO:
            }else{
                //todo 点击搜索
                searchFragment= SearchFragment()
                searchFragment.show(supportFragmentManager, SEARCH_TAG)
            }
        }

    }

    fun initFragment(savedInstanceState: Bundle?){

    }


    override fun onClick(v: View?) {
    }

    private fun getToday():String{

        return ""
    }
}
