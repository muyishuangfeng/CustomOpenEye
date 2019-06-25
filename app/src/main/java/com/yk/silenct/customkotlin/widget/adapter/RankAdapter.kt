package com.yk.silenct.customkotlin.widget.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.mvp.model.bean.HotBean
import com.yk.silenct.customkotlin.mvp.model.bean.VideoBean
import com.yk.silenct.customkotlin.util.ImageLoadUtils
import com.yk.silenct.customkotlin.util.ObjectSaveUtils
import com.yk.silenct.customkotlin.util.SPUtils
import com.yk.silenct.customkotlin.widget.activity.VideoDetailActivity

class RankAdapter(context: Context, list: List<HotBean.ItemListBean.DataBean>) : RecyclerView.Adapter<RankAdapter.RankViewHolder>() {

    var mContext: Context? = null
    var mInflater: LayoutInflater? = null
    var mList: List<HotBean.ItemListBean.DataBean>? = null

    init {
        mContext = context
        mList = list
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        return RankViewHolder(mInflater?.inflate(R.layout.item_rank, parent, false), mContext!!)
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        var photoUrl: String? = mList?.get(position)?.cover?.feed
        photoUrl?.let { ImageLoadUtils.display(mContext!!, holder.mImgPhoto, it) }
        var title: String? = mList?.get(position)?.title
        holder.mTxtTitle.text = title
        var category = mList?.get(position)?.category
        var duration = mList?.get(position)?.duration
        //duration/60
        var minute = duration?.div(60)
        var second = duration?.minus((minute?.times(60)) as Long)
        var realMinute: String? = null
        var realSecond: String? = null
        if (minute!! < 10) {
            realMinute = "0" + minute
        } else {
            realMinute = minute.toString()
        }
        if (second!! < 10) {
            realSecond = "0" + second
        } else {
            realMinute = second.toString()
        }
        holder.mTxtTime.text = "$category/ $realMinute'$realSecond''"
        holder.itemView.setOnClickListener {
            var intent: Intent = Intent(mContext, VideoDetailActivity::class.java)
            var desc = mList?.get(position)?.description
            var playUrl = mList?.get(position)?.playUrl
            var blurred = mList?.get(position)?.cover?.blurred
            var collect = mList?.get(position)?.consumption?.conllection
            var share = mList?.get(position)?.consumption?.shareCount
            var reply = mList?.get(position)?.consumption?.reployCount
            var time = System.currentTimeMillis()
            var videoBean = VideoBean(photoUrl, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
            var url = SPUtils.getInstance(mContext!!, "beans").getString(playUrl!!)
            if (TextUtils.isEmpty(url)) {
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
            mContext.let { mContext -> mContext?.startActivity(intent) }
        }
    }

    /**
     * ViewHolder
     */
    class RankViewHolder(itemView: View?, context: Context) : RecyclerView.ViewHolder(itemView!!) {
        var mImgPhoto: AppCompatImageView = itemView?.findViewById(R.id.img_rank_photo) as AppCompatImageView
        var mTxtTitle: TextView = itemView?.findViewById(R.id.txt_rank_title) as TextView
        var mTxtTime: TextView = itemView?.findViewById(R.id.txt_rank_time) as TextView
    }
}