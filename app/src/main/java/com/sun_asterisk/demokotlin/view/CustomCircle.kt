package com.sun_asterisk.demokotlin.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CustomCircle : View {
    private var circleX: Float = 0f
    private var circleY: Float = 0f
    private var radiusCircle: Float = 100f
    private lateinit var paint: Paint

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs)
    }

    private fun init(set: AttributeSet?) {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (circleX == 0f || circleY == 0f) {
            circleX = width / 2f
            circleY = height / 2f
        }
        canvas?.drawCircle(circleX, circleY, radiusCircle, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val value = super.onTouchEvent(event)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                // sự kiện click sẽ làm gì đó
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y
                val dx = Math.pow((x - circleX).toDouble(), 2.0)
                val dy = Math.pow((y - circleY).toDouble(), 2.0)
                if (dx + dy < Math.pow(radiusCircle.toDouble(), 2.0)) {
                    //Touched
                    circleX = x
                    circleY = y
                    postInvalidate()
                    return true
                }
                return value
            }
        }
        return value
    }
}