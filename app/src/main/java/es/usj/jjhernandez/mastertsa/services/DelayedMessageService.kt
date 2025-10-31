package es.usj.jjhernandez.mastertsa.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.widget.Toast

const val KEY = "MSG_KEY"

class DelayedMessageService : JobService() {

    private fun showText(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        synchronized(this) {
            val message = params?.extras?.getString(KEY)
            showText("Delayed message: $message")
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }
}