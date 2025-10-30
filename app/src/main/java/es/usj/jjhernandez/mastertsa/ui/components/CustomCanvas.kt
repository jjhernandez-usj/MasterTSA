package es.usj.jjhernandez.mastertsa.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import es.usj.jjhernandez.mastertsa.MainActivity

class CustomCanvas(context: Context): View(context) {

    private val brush: Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRGB(255, 255, 0)
        brush.apply {
            style = Paint.Style.STROKE
            strokeWidth = 4f
            setARGB(255, 255, 0 ,0)
        }
        val activity = context as MainActivity
        canvas.drawCircle(activity.cx, activity.cy, 20f, brush)
    }
}