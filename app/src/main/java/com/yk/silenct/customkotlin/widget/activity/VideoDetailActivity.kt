package com.yk.silenct.customkotlin.widget.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.mvp.model.bean.VideoBean
import com.yk.silenct.customkotlin.util.*
import kotlinx.android.synthetic.main.activity_video_detail.*
import zlc.season.rxdownload2.RxDownload
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.concurrent.ExecutionException

class VideoDetailActivity : AppCompatActivity() {

    //静态方法
    companion object {
        var MSG_IMAGE_LOADED = 101
    }

    var mContext: Context = this
    lateinit var mImage: ImageView
    lateinit var mBean: VideoBean
    //是否播放
    var isPlay: Boolean = false
    //是否暂停
    var isPause: Boolean = false
    //处理屏幕旋转的工具类
    lateinit var orientationUtils: OrientationUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_video_detail)
        initView()
        prepareVideo()
    }

    /**
     * 初始化控件
     */
    @SuppressLint("SetTextI18n")
    fun initView() {
        mBean = intent.getParcelableExtra("data")
        var bgUrl = mBean.blurred
        bgUrl?.let { ImageLoadUtils.display(this, img_video_bg, bgUrl) }

        txt_video_desc.text = mBean.description
        txt_video_title.text = mBean.title
        var category = mBean.category
        var duration = mBean.duration
        var minute = duration?.div(60)
        var second = duration?.minus((minute?.times(60) as Long))
        var realMinute: String
        var realSecond: String
        if (minute!! < 10) {
            realMinute = "0$minute"
        } else {
            realMinute = minute.toString()
        }
        if (second!! < 10) {
            realSecond = "0$second"
        } else {
            realSecond = second.toString()
        }
        txt_video_time.text = "$category/$realMinute'$realSecond''"
        txt_video_favor.text = mBean.collection.toString()
        txt_video_share.text = mBean.share.toString()
        txt_video_reply.text = mBean.reply.toString()
        txt_video_download.setOnClickListener {
            //下载
            var url = mBean.playUrl?.let { it1 ->
                SPUtils.getInstance(this, "downloads").getString(it1)
            }
            if (TextUtils.isEmpty(url)) {
                var count = SPUtils.getInstance(this, "downloads").getInt("count")
                if (count != -1) {
                    //count++
                    count.inc()
                } else {
                    count = 1
                }
                SPUtils.getInstance(this, "downloads").put("count", count)
                ObjectSaveUtils.saveObject(this, "download$count", mBean)
                //添加任务
                addMission(mBean.playUrl, count)
            } else {
                showToast(resources.getString(R.string.text_already_catch))
            }
        }


    }

    /**
     * 添加任务
     */
    @SuppressLint("CheckResult")
    fun addMission(playUrl: String?, count: Int) {
        RxDownload.getInstance(this).serviceDownload(playUrl, "download$count")
                .subscribe({
                    showToast(resources.getString(R.string.text_start_download))
                    SPUtils.getInstance(this, "downloads")
                            .put(mBean.playUrl.toString(), mBean.playUrl.toString())
                    SPUtils.getInstance(this, "download_state")
                            .put(mBean.playUrl.toString(), true)
                }, {
                    showToast(resources.getString(R.string.text_add_task_failed))
                })
    }

    /**
     * 视频准备
     */
    private fun prepareVideo() {
        var uri = intent.getStringExtra("loaclFile")
        if (uri != null) {
            gsy_player.setUp(uri, false, null, null)
        } else {
            gsy_player.setUp(mBean.playUrl, false, null, null)
        }
        //增加封面
        mImage = ImageView(this)
        mImage.scaleType = ImageView.ScaleType.CENTER_CROP
        if (!TextUtils.isEmpty(mBean.feed)){
            ImageAsyncTask(mHandler, this, mImage).execute(mBean.feed)
        }
        gsy_player.titleTextView.visibility = View.GONE
        gsy_player.backButton.visibility = View.VISIBLE
        orientationUtils = OrientationUtils(this, gsy_player)
        gsy_player.setIsTouchWiget(true);
        //关闭自动旋转
        gsy_player.isRotateViewAuto = false;
        gsy_player.isLockLand = false;
        gsy_player.isShowFullAnimation = false;
        gsy_player.isNeedLockFull = true;
        gsy_player.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils.resolveByClick();
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            gsy_player.startWindowFullscreen(mContext, true, true);
        }
        gsy_player.setStandardVideoAllCallBack(object : VideoListener() {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils.isEnable = true
                isPlay = true;
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                super.onAutoComplete(url, *objects)

            }

            override fun onClickStartError(url: String?, vararg objects: Any?) {
                super.onClickStartError(url, *objects)
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils?.let { orientationUtils.backToProtVideo() }
            }
        })
        gsy_player.setLockClickListener { view, lock ->
            //配合下方的onConfigurationChanged
            orientationUtils.isEnable = !lock
        }
        gsy_player.backButton.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })


    }


    /**
     * Handler
     */
    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun dispatchMessage(msg: Message?) {
            super.dispatchMessage(msg)
            when (msg?.what) {
                MSG_IMAGE_LOADED -> {
                    //设置封面
                    gsy_player.setThumbImageView(mImage)
                }
            }

        }
    }

    /**
     * 异步下载
     */
    @SuppressLint("StaticFieldLeak")
    private class ImageAsyncTask(handler: Handler, activity: VideoDetailActivity, private val mImageView: ImageView)
        : AsyncTask<String, Void, String>() {
        private var handler = handler
        private var mPath: String? = null
        private var fis: FileInputStream? = null
        private var mActivity: VideoDetailActivity = activity

        override fun doInBackground(vararg params: String): String? {
            val futrue = Glide.with(mActivity)
                    .load(params[0])
                    .downloadOnly(100, 100)
            try {
                val cacheFile = futrue.get()
                mPath = cacheFile.absolutePath
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }
            return mPath
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                try {
                    fis = FileInputStream(result)
                } catch (ex: FileNotFoundException) {
                    ex.printStackTrace()
                }
            }

            val bitmap = BitmapFactory.decodeStream(fis)
            mImageView.setImageBitmap(bitmap)
            var message = handler.obtainMessage()
            message.what = MSG_IMAGE_LOADED
            handler.sendMessage(message)
        }
    }

    override fun onBackPressed() {
        orientationUtils.let { orientationUtils.backToProtVideo() }
        /**
         * 退出全屏，主要用于返回键
         *
         * @return 返回是否全屏
         */
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        isPause = true
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils.let { orientationUtils.releaseListener() }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!gsy_player.isIfCurrentIsFullscreen) {//不是全屏
                    gsy_player.startWindowFullscreen(this, true, true)
                } else {
                    //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                    if (gsy_player.isIfCurrentIsFullscreen) {
                        //退出全屏，主要用于返回键
                        StandardGSYVideoPlayer.backFromWindowFull(this)
                    }
                    orientationUtils.let { orientationUtils.isEnable = true }
                }
            }
        }
    }
}
