package com.yk.silenct.customkotlin.widget.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.yk.silenct.customkotlin.R
import kotlinx.android.synthetic.main.activity_find_detail.*

class FindDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_detail)
        initView()
    }

    /**
     * 初始化数据
     */
    fun initView() {
        if (!TextUtils.isEmpty(intent.getStringExtra("name"))){
            var name = intent.getStringExtra("name")
            txt_find_detail.text = name
        }
    }
}
