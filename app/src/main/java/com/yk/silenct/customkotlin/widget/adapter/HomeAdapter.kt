package com.yk.silenct.customkotlin.widget.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.mvp.model.bean.HomeBean
import com.yk.silenct.customkotlin.mvp.model.bean.VideoBean
import com.yk.silenct.customkotlin.util.ImageLoadUtils
import com.yk.silenct.customkotlin.util.ObjectSaveUtils
import com.yk.silenct.customkotlin.util.SPUtils
import com.yk.silenct.customkotlin.widget.activity.VideoDetailActivity
import kotlinx.android.synthetic.main.item_home_layout.view.*

class HomeAdapter(context: Context, list: MutableList<HomeBean.IssueItemBean.ItemListBean>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var mContext: Context? = null
    var mList: MutableList<HomeBean.IssueItemBean.ItemListBean>? = null
    var mInflater: LayoutInflater? = null

    init {
        mContext = context
        mList = list
        mInflater = LayoutInflater.from(mContext)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(mInflater?.inflate(R.layout.item_home_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        var bean = mList?.get(position)
        var title = bean?.data?.title
        var category = bean?.data?.category
        var minute = bean?.data?.duration?.div(60)
        var second = bean?.data?.duration?.minus((minute?.times(60)) as Long)
        var realMinute: String
        var realSecond: String
        if (minute!! < 10) {
            realMinute = "0" + minute
        } else {
            realMinute = minute.toString()
        }
        if (second!! < 10) {
            realSecond = "0" + second
        } else {
            realSecond = second.toString()
        }

        holder.itemView.tv_title.text = title
        var photo = bean?.data?.cover?.feed
        var author = bean?.data?.author
        ImageLoadUtils.display(mContext!!, holder.itemView.iv_photo, photo as String)
        holder.itemView.tv_detail?.text = "发布于 $category / $realMinute:$realSecond"
        if (author != null) {
            ImageLoadUtils.display(mContext!!, holder.itemView.iv_user, author.icon as String)
        } else {
            holder.itemView.iv_user.visibility = View.GONE
        }
        holder.itemView.setOnClickListener {
            //跳转视频详情页
            var intent: Intent = Intent(mContext, VideoDetailActivity::class.java)
            var desc = bean?.data?.description
            var duration = bean?.data?.duration
            var playUrl = bean?.data?.playUrl
            var blurred = bean?.data?.cover?.blurred
            var collect = bean?.data?.consumption?.collectionCount
            var share = bean?.data?.consumption?.shareCount
            var reply = bean?.data?.consumption?.replyCount
            var time = System.currentTimeMillis()
            var videoBean = VideoBean(photo, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
            var url = SPUtils.getInstance(mContext!!, "beans").getString(playUrl!!)
            if (url.equals("")) {
                var count = SPUtils.getInstance(mContext!!, "beans").getInt("count")
                if (count != -1) {
                    count = count.inc()
                } else {
                    count = 1
                }
                SPUtils.getInstance(mContext!!, "beans").put("count", count)
                SPUtils.getInstance(mContext!!, "beans").put(playUrl, playUrl)
                ObjectSaveUtils.saveObject(mContext!!, "bean$count", videoBean)
            }
            intent.putExtra("data", videoBean as Parcelable)
            mContext.let { context -> mContext?.startActivity(intent) }
        }


    }

    /**
     * viewHolder
     */
    class HomeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}