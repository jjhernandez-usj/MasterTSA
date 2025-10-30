package es.usj.jjhernandez.mastertsa

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.usj.jjhernandez.mastertsa.databinding.ActivityMainBinding
import es.usj.jjhernandez.mastertsa.ui.components.CustomCanvas

class MainActivity : AppCompatActivity(), View.OnTouchListener {

    var cx: Float = 100f
    var cy: Float = 100f

    lateinit var canvas: CustomCanvas

    private val view by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(view.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.canvasLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        canvas = CustomCanvas(this)
        canvas.setOnTouchListener(this)
        view.canvasLayout.addView(canvas)
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        view?.performClick()
        cx = event?.x ?: 0f
        cy = event?.y ?: 0f
        canvas.invalidate()
        return true
    }
}

