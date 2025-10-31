package es.usj.jjhernandez.mastertsa

import android.Manifest
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import es.usj.jjhernandez.mastertsa.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var deltaXMax = 0f
    private var deltaYMax = 0f
    private var deltaZMax = 0f
    private var deltaX = 0f
    private var deltaY = 0f
    private var deltaZ = 0f
    private var vibrateThreshold = 0f
    lateinit var v: Vibrator
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    private val view by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(view.root)
        supportActionBar?.hide()
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if(sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager!!.registerListener(
                this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL
            )
            vibrateThreshold = accelerometer!!.maximumRange / 2
        }
        val vibratorManager = this.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
        v = vibratorManager.defaultVibrator
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(
            this, accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    @RequiresPermission(Manifest.permission.VIBRATE)
    override fun onSensorChanged(event: SensorEvent) {
        displayCleanValues()
        displayCurrentValues()
        displayMaxValues()
        deltaX = abs(lastX - event.values[0])
        deltaY = abs(lastY - event.values[1])
        deltaZ = abs(lastZ - event.values[2])
        if (deltaX < 2)
            deltaX = 0f
        if (deltaY < 2)
            deltaY = 0f
        if (deltaZ < 2)
            deltaZ = 0f
        lastX = event.values[0]
        lastY = event.values[1]
        lastZ = event.values[2]
        vibrate()
    }

    private fun displayCleanValues() {
        view.currentX.text = "0.0"
        view.currentY.text = "0.0"
        view.currentZ.text = "0.0"
    }
    private fun displayCurrentValues() {
        view.currentX.text = deltaX.toString()
        view.currentY.text = deltaY.toString()
        view.currentZ.text = deltaZ.toString()
    }

    private fun displayMaxValues() {
        if (deltaX > deltaXMax) {
            deltaXMax = deltaX
            view.maxX.text = deltaXMax.toString()
        }
        if (deltaY > deltaYMax) {
            deltaYMax = deltaY
            view.maxY.text = deltaYMax.toString()
        }
        if (deltaZ > deltaZMax) {
            deltaZMax = deltaZ
            view.maxZ.text = deltaZMax.toString()
        }
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun vibrate() {
        if (deltaX > vibrateThreshold || deltaY > vibrateThreshold
            ||
            deltaZ > vibrateThreshold) {
            v.vibrate(
                VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK))
        }
    }
}