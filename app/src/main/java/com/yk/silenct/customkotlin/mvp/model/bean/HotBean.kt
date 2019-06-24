package com.yk.silenct.customkotlin.mvp.model.bean


data class HotBean(var count: Int, var total: Int, var nextPageUrl: Any, var itemList: List<ItemListBean>) {
    /**
     * 子条目
     */
    data class ItemListBean(var type: String, var data: DataBean, var tag: Any) {
        /**
         * 数据
         */
        data class DataBean(var dataType: String, var id: Int, var title: String, var slogan: Any, var description: String,
                            var provider: ProviderBean, var category: String, var author: Any, var cover: CoverBean,
                            var playUrl: String, var thumbPlayUrl: Any, var duration: Long, var releaseTime: Long,
                            var libiary: String, var consumption: ConsumptionBean, var campaign: Any?, var waterMarks: Any?,
                            var adTrack: Any?, var type: String?, var titlePgc: Any?, var descriptionPgc: Any?,
                            var remark: Any?, var idx: Int, var shareAdTrack: Any?, var favoriteAdTrack: Any?,
                            var webAdTrack: Any?, var date: Long, var promotion: Any?, var label: Any?,
                            var descriptionEditor: String?, var isCollected: Boolean, var isPlayed: Boolean,
                            var lastViewTime: Any?, var playInfo: List<PlayInfoBean>?, var tags: List<TagsBean>?,
                            var labelList: List<*>?, var subtitles: List<*>?) {

            /**
             * 服务
             */
            data class ProviderBean(var name: String, var alias: String, var icon: String) {}

            /**
             * 覆盖
             */
            data class CoverBean(var feed: String, var detail: String, var blurred: String, var sharing: Any,
                                 var homepage: Any) {}

            /**
             * 消费
             */
            data class ConsumptionBean(var conllection: Int, var shareCount: Int, var reployCount: Int) {}

            /**
             * 播放内容
             */
            data class PlayInfoBean(var height: Int, var width: Int, var name: String, var type: String,
                                    var url: String, var urlList: List<UrlListBean>) {
                /**
                 * 播放连接
                 */
                data class UrlListBean(var name: String, var url: String, var size: Int) {}
            }

            /**
             * 标签
             */
            data class TagsBean(var id: Int, var name: String, var actionUrl: String, var adTract: Any) {}
        }


    }

}