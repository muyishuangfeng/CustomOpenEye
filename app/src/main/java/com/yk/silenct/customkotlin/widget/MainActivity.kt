package com.yk.silenct.customkotlin.widget

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yk.silenct.customkotlin.R

class MainActivity : AppCompatActivity() ,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    override fun onClick(v: View?) {
    }

}
