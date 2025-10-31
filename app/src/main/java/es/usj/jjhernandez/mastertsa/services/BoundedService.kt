package es.usj.jjhernandez.mastertsa.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer


class BoundedService : Service() {

    inner class BoundServiceBinder : Binder() {
        internal val service: BoundedService
            get() = this@BoundedService
    }

    private val LOG_TAG = "BoundService"
    private val mBinder = BoundServiceBinder()
    private var mChronometer: Chronometer? = null

    override fun onCreate() {
        super.onCreate()
        Log.v(LOG_TAG, "in onCreate")
        mChronometer = Chronometer(this)
        mChronometer!!.base = SystemClock.elapsedRealtime()
        mChronometer!!.start()
    }

    override fun onBind(intent: Intent): IBinder {
        Log.v(LOG_TAG, "in onBind")
        return mBinder
    }

    override fun onRebind(intent: Intent) {
        Log.v(LOG_TAG, "in onRebind")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.v(LOG_TAG, "in onUnbind")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(LOG_TAG, "in onDestroy")
        mChronometer!!.stop()
    }

    fun getTimestamp(): String {
        val elapsedMillis = SystemClock.elapsedRealtime() -
                mChronometer!!.base
        val hours = (elapsedMillis / 3600000).toInt()
        val minutes = (elapsedMillis - hours * 3600000).toInt() /
                60000
        val seconds =
            (elapsedMillis - (hours * 3600000).toLong() - (minutes *
                    60000).toLong()).toInt() / 1000
        val millis =
            (elapsedMillis - (hours * 3600000).toLong() - (minutes *
                    60000).toLong() - (seconds * 1000).toLong()).toInt()
        return "$hours:$minutes:$seconds:$millis"
    }
}