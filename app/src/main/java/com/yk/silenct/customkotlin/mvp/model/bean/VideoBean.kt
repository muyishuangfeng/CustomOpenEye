package com.yk.silenct.customkotlin.mvp.model.bean

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class VideoBean(var feed: String?, var title: String?, var description: String?, var duration: Long?,
                     var playUrl: String?, var category: String?, var blurred: String?, var collection: Int?,
                     var share: Int?, var reply: Int?, var time: Long?) : Parcelable, Serializable {


    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Long::class.java.classLoader) as? Long) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(feed)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeValue(duration)
        parcel.writeString(playUrl)
        parcel.writeString(category)
        parcel.writeString(blurred)
        parcel.writeValue(collection)
        parcel.writeValue(share)
        parcel.writeValue(reply)
        parcel.writeValue(time)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<VideoBean> {
        override fun createFromParcel(parcel: Parcel): VideoBean {
            return VideoBean(parcel)
        }

        override fun newArray(size: Int): Array<VideoBean?> {
            return arrayOfNulls(size)
        }
    }
}