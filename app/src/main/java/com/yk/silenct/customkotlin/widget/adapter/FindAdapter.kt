package com.yk.silenct.customkotlin.widget.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yk.silenct.customkotlin.R
import com.yk.silenct.customkotlin.impl.OnItemClickListener
import com.yk.silenct.customkotlin.mvp.model.bean.FindBean
import com.yk.silenct.customkotlin.util.ImageLoadUtils
import kotlinx.android.synthetic.main.item_find_layout.view.*

class FindAdapter(context: Context, list: MutableList<FindBean>) : RecyclerView.Adapter<FindAdapter.FindViewHolder>() {

    var mContext: Context? = null
    var mList: MutableList<FindBean>? = null
    var mInflater: LayoutInflater? = null
    var mListener: OnItemClickListener? = null

    init {
        this.mContext = context
        this.mList = list
        this.mInflater = LayoutInflater.from(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindViewHolder {
        return FindViewHolder(mInflater?.inflate(R.layout.item_find_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: FindViewHolder, position: Int) {
        holder.itemView.tv_item_title.text=mList?.get(position)?.name
        //holder.mTxtTitle.text = mList?.get(position)?.name
        //ImageLoadUtils.display(mContext!!, holder.mImgPicture, mList?.get(position)?.bgPicture!!)
        ImageLoadUtils.display(mContext!!, holder.itemView.iv_item_photo, mList?.get(position)?.bgPicture!!)
        if (mListener != null) {
            holder.itemView.setOnClickListener {
                mListener!!.onItemClick(holder.itemView, position)
            }
        }
    }

    /**
     * viewHolder
     */
    class FindViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        //var mTxtTitle: TextView = itemView?.findViewById(R.id.tv_item_title) as TextView
        //var mImgPicture: AppCompatImageView = itemView?.findViewById(R.id.iv_item_photo) as AppCompatImageView
    }

    /**
     * 子条目点击事件回调
     */
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

}