package com.yk.silenct.customkotlin.widget.activity

import android.annotation.SuppressLint
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.barlibrary.ImmersionBar
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.mvp.model.bean.VideoBean
import com.yk.silenct.customkotlin.util.ObjectSaveUtils
import com.yk.silenct.customkotlin.util.SPUtils
import com.yk.silenct.customkotlin.widget.adapter.DownloadAdapter
import kotlinx.android.synthetic.main.activity_cache.*
import zlc.season.rxdownload2.RxDownload

class CacheActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_cache)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        setToolbar()
        DataAsyncTask(mHandler, this).execute()
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = DownloadAdapter(this, mList)
        mAdapter.setOnLongClickListener(object : DownloadAdapter.OnLongClickListener {
            override fun onLongClick(position: Int) {
                addDialog(position)
            }
        })

        recyclerView.adapter = mAdapter
    }

    var mList = ArrayList<VideoBean>()
    lateinit var mAdapter: DownloadAdapter
    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            var list = msg?.data?.getParcelableArrayList<VideoBean>("beans")
            if (list?.size?.compareTo(0) == 0) {
                tv_hint.visibility = View.VISIBLE
            } else {
                tv_hint.visibility = View.GONE
                if (mList.size > 0) {
                    mList.clear()
                }
                list?.let { mList.addAll(it) }
                mAdapter.notifyDataSetChanged()
            }

        }
    }

    /**
     * 对话框
     */
    private fun addDialog(position: Int) {
        var builder = AlertDialog.Builder(this)
        var dialog = builder.create()
        builder.setMessage("是否删除当前视频")
        builder.setNegativeButton("否", { dialog, which ->
            dialog.dismiss()
        })
        builder.setPositiveButton("是", { dialog, which ->
            deleteDownload(position)
        })
        builder.show()
    }

    /**
     * 删除下载
     */
    private fun deleteDownload(position: Int) {
        RxDownload.getInstance(this@CacheActivity).deleteServiceDownload(mList[position].playUrl, true).subscribe()
        SPUtils.getInstance(this, "downloads").put(mList[position].playUrl.toString(), "")
        var count = position + 1
        ObjectSaveUtils.deleteFile("download$count", this)
        mList.removeAt(position)
        mAdapter.notifyItemRemoved(position)
    }

    private fun setToolbar() {
        var bar = supportActionBar
        bar?.title = "我的缓存"
        bar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    /**
     * 异步下载
     */
    @SuppressLint("StaticFieldLeak")
    private class DataAsyncTask(handler: Handler, activity: CacheActivity) : AsyncTask<Void, Void, ArrayList<VideoBean>>() {
        var activity: CacheActivity = activity
        var handler = handler
        override fun doInBackground(vararg params: Void?): ArrayList<VideoBean>? {
            var list = ArrayList<VideoBean>()
            var count: Int = SPUtils.getInstance(activity, "downloads").getInt("count")
            var i = 1
            while (i.compareTo(count) <= 0) {
                var bean: VideoBean
                if (ObjectSaveUtils.getValue(activity, "download$i") == null) {
                    continue
                } else {
                    bean = ObjectSaveUtils.getValue(activity, "download$i") as VideoBean
                }
                list.add(bean)
                i++
            }
            return list
        }

        override fun onPostExecute(result: ArrayList<VideoBean>?) {
            super.onPostExecute(result)
            var message = handler.obtainMessage()
            var bundle = Bundle()
            bundle.putParcelableArrayList("beans", result)
            message.data = bundle
            handler.sendMessage(message)
        }

    }
}
