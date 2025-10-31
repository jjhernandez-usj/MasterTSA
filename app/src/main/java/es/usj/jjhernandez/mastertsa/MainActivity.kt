package es.usj.jjhernandez.mastertsa

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.usj.jjhernandez.mastertsa.databinding.ActivityMainBinding
import es.usj.jjhernandez.mastertsa.services.DelayedMessageService
import es.usj.jjhernandez.mastertsa.services.KEY

class MainActivity : AppCompatActivity() {

    private val view by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(view.root)

        view.btnSendMessage.setOnClickListener {
            val message = view.etMessage.text.toString()
            val serviceComponent = ComponentName(
                this,
                DelayedMessageService::class.java
            )
            val builder = JobInfo.Builder(0, serviceComponent)
            builder.setMinimumLatency((3 * 1000).toLong())
            builder.setOverrideDeadline((6 * 1000).toLong())
            builder.setExtras(
                PersistableBundle().apply {
                    putString(KEY, message)
                }
            )
            val service = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            service.schedule(builder.build())
        }
    }
}