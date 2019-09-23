package com.sun_asterisk.demokotlin.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.sun_asterisk.demokotlin.R

class CustomSquare : View {
    private val VALUE_DEFAULE = 0
    private lateinit var rect : Rect
    private lateinit var paint: Paint
    private var squareColor : Int = VALUE_DEFAULE
    private var squareSize : Int = VALUE_DEFAULE

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rect.top = 100
        rect.left = 100
        rect.right = rect.left + 100
        rect.bottom = rect.top + 100

        canvas.drawRect(rect,paint)
    }

    fun swapColor(){
        if (paint.color == squareColor){
            paint.color = Color.RED
        }else{
            paint.color = squareColor
        }
        postInvalidate()
    }

    private fun init(set: AttributeSet?) {
        rect = Rect()
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        if (set == null)
            return
        val ta : TypedArray = context.obtainStyledAttributes(set, R.styleable.CustomSquare)
        squareColor = ta.getColor(R.styleable.CustomSquare_square_color,Color.GREEN)
        squareSize = ta.getDimensionPixelSize(R.styleable.CustomSquare_square_size,VALUE_DEFAULE)
        paint.color = squareColor
        ta.recycle()
    }
}
