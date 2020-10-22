package com.renestim.aniwaplus.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.renestim.aniwaplus.R
import java.text.DecimalFormat


class Utils {
    companion object {
        internal val DF = DecimalFormat("0.00")
        internal val nameSpace= "http://renestim.com/"
        internal val nameAsmx= "AniwaPluser.asmx"
       // val defUrl="http://www.renessoft.org/"
        val defUrl="http://192.168.1.111:5000/"

        fun setorgurl(context: Context, orgurl: String?) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putString(context.getString(R.string.prefs_url), orgurl).apply()
        }

        fun getorgurl(context: Context): String? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(context.getString(R.string.prefs_url), defUrl)
        }

        fun setEA(context: Context, ea: String?) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putString(context.getString(R.string.prefs_ea), ea).apply()
        }

        fun getEA(context: Context): String? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(context.getString(R.string.prefs_ea), "yok")
        }
        fun setUid(context: Context, ea: String?) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putString(context.getString(R.string.prefs_uid), ea).apply()
        }

        fun getUid(context: Context): String? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(context.getString(R.string.prefs_uid), "yok")
        }


        fun setreklamsay(context: Context, bildirim: Int) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putInt(context.getString(R.string.prefs_reklamsay), bildirim).apply()
        }

        fun getreklamsay(context: Context): Int {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getInt(context.getString(R.string.prefs_reklamsay), 5)
        }

        fun setreklamgun(context: Context, bildirim: Long) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putLong(context.getString(R.string.prefs_reklamgun), bildirim).apply()
        }

        fun getreklamgun(context: Context): Long {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getLong(context.getString(R.string.prefs_reklamgun), 0)
        }

        fun setreklams(context: Context, reklam: String?) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putString(context.getString(R.string.prefs_reklam), reklam).apply()
        }

        fun getreklams(context: Context): String? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(context.getString(R.string.prefs_reklam), "acik")
        }
        fun setea(context: Context, ea: String?) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putString(context.getString(R.string.prefs_ea), ea).apply()
        }

        fun getea(context: Context): String? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(context.getString(R.string.prefs_ea), "yok")
        }


        fun setusername(context: Context, ea: String?) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putString(context.getString(R.string.prefs_username), ea).apply()
        }

        fun getusername(context: Context): String? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(context.getString(R.string.prefs_username), "")
        }


        fun setWid(context: Context, theme: Int) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putInt(context.getString(R.string.prefs_wid), theme).apply()
        }

        fun getWid(context: Context): Int {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getInt(context.getString(R.string.prefs_wid), 0)
        }


        fun setuserimage(context: Context, ea: String?) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putString(context.getString(R.string.prefs_userimage), ea).apply()
        }

        fun getuserimage(context: Context): String? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(context.getString(R.string.prefs_userimage), "")
        }


        fun setfirst(context: Context, bildirim: Boolean) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            prefs.edit().putBoolean(context.getString(R.string.prefs_ilk), bildirim).apply()
        }

        fun getfirst(context: Context): Boolean {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getBoolean(context.getString(R.string.prefs_ilk), true)
        }

    }
}