package es.usj.jjhernandez.mastertsa

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import es.usj.jjhernandez.mastertsa.databinding.ActivityMainBinding
import es.usj.jjhernandez.mastertsa.services.BoundedService

class MainActivity : AppCompatActivity() {

    private val view by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var mBoundService: BoundedService
    internal var mServiceBound = false

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            mServiceBound = false
        }
        override fun onServiceConnected(name: ComponentName,
                                        service: IBinder
        ) {
            val myBinder = service as
                    BoundedService.BoundServiceBinder
            mBoundService = myBinder.service
            mServiceBound = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(view.root)
        supportActionBar?.hide()
        view.btnStop.setOnClickListener { stop() }
        view.btnPrintTimestamp.setOnClickListener {
            printTimestamp() }
    }

    private fun stop() {
        if (mServiceBound) {
            unbindService(mServiceConnection)
            mServiceBound = false
        }
        val intent = Intent(
            this@MainActivity,
            BoundedService::class.java
        )
        stopService(intent)
    }
    private fun printTimestamp() {
        if (mServiceBound) {
            view.tv1.text = mBoundService.getTimestamp()
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, BoundedService::class.java)
        startService(intent)
        bindService(intent, mServiceConnection,
            BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mServiceBound) {
            unbindService(mServiceConnection)
            mServiceBound = false
        }
    }
}