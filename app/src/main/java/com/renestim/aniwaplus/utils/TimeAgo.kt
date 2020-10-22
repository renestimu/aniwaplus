package com.renestim.aniwaplus.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class TimeAgo {

companion object{
    var SECOND_MILLIS:Int=1000
    var MINUTE_MILLIS:Int=60 * SECOND_MILLIS
    var HOUR_MILLIS=60 * MINUTE_MILLIS
    var DAY_MILLIS=24 * HOUR_MILLIS
    fun getTimeAgo( time:Long):String{
        var timer=time

        if (timer < 1000000000000L) {
            timer *= 1000
        }
        var now = System.currentTimeMillis();
        if (timer > now || timer <= 0) {
            return "";
        }
        var diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "Az önce";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "Bir dakika önce";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return (diff / MINUTE_MILLIS).toString() + " dakika önce";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "Birkaç saat önce";
        } else if (diff < 24 * HOUR_MILLIS) {
            return (diff / HOUR_MILLIS).toString() + " saat önce";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Dün";
        } else {
            try {
                var format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale("tr"));
                var parsedate=format.parse(time.toString())
                var timestamp=java.sql.Time(parsedate.time)
                return timestamp.toString();
            }catch (ex:Exception){
                return ""
            }

        }



    }
}
}