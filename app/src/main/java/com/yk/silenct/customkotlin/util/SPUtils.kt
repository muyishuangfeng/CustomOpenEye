package com.yk.silenct.customkotlin.util

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import kotlin.collections.HashMap

@Suppress("NAME_SHADOWING")
class SPUtils private constructor(context: Context, spName: String) {

    private val sp: SharedPreferences

    init {
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    /**
     * sp中写入String
     *
     * @param key 键
     * @param value 值
     */
    fun put(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    /**
     * sp中读取String
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getString(key: String, defaultValue: String = ""): String? {
        return sp.getString(key, defaultValue)
    }


    /**
     * sp中写入Int
     *
     * * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Int) {
        sp.edit().putInt(key, value).apply()
    }

    /**
     * sp中读取Int
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getInt(key: String, defaultValue: Int = -1): Int {
        return sp.getInt(key, defaultValue)
    }


    /**
     * sp中写入Boolean
     *
     * * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Boolean) {
        sp.edit().putBoolean(key, value).apply()
    }

    /**
     * sp中读取Boolean
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    /**
     * sp中写入Float
     *
     * * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Float) {
        sp.edit().putFloat(key, value).apply()
    }

    /**
     * sp中读取Float
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getFloat(key: String, defaultValue: Float = -1f): Float {
        return sp.getFloat(key, defaultValue)
    }

    /**
     * sp中写入Long
     *
     * * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Long) {
        sp.edit().putLong(key, value).apply()
    }

    /**
     * sp中读取String
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getLong(key: String, defaultValue: Long = -1L): Long {
        return sp.getLong(key, defaultValue)
    }

    /**
     * sp中写入String集合
     *
     * * @param key 键
     * @param value 值
     */
    fun put(key: String, value: Set<String>) {
        sp.edit().putStringSet(key, value).apply()
    }

    /**
     * sp中读取String集合
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getStringSet(key: String, defaultValue: Set<String> = Collections.emptySet()): Set<String>? {
        return sp.getStringSet(key, defaultValue)
    }

    /**
     * SP中获取所有键值对
     * @return Map对象
     */
    val all: Map<String, *>
        get() = sp.all

    /**
     *SP中移除该key
     *
     * @param key 键
     */
    fun remove(key: String) {
        sp.edit().remove(key).apply()
    }

    /**
     *SP中清除所有数据
     */
    fun clear() {
        sp.edit().clear().apply()
    }

    /**
     * SP中是否存在该key
     * @param key 键
     * *
     * @return `true`: 存在<br></br>`false`: 不存在
     */
    operator fun contains(key: String): Boolean {
        return sp.contains(key)
    }

    //静态成员，静态方法
    companion object {

        private val mSpMap = HashMap<String, SPUtils>()

        /**
         * 获取SP实例
         * @param spName sp名
         * *
         * @return [SPUtils]
         */
        fun getInstance(context: Context, spName: String): SPUtils {
            var spName = spName
            if (isSpace(spName)) spName = "spUtils"
            var sp: SPUtils? = mSpMap[spName]
            if (sp == null) {
                sp = SPUtils(context, spName)
                mSpMap.put(spName, sp)
            }
            return sp
        }

        private fun isSpace(s: String?): Boolean {
            if (s == null) return true
            var i = 0
            val len = s.length
            while (i < len) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
                ++i
            }
            return true
        }

    }

}