package com.sun_asterisk.demokotlin.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import com.sun_asterisk.demokotlin.R

class CustomImageView : View, ViewTreeObserver.OnGlobalLayoutListener{
    private lateinit var bitmap: Bitmap
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
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.tarzan_bg)
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, 0f, 0f, null)
    }

    override fun onGlobalLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) viewTreeObserver.removeOnGlobalLayoutListener(this)
        else viewTreeObserver.removeGlobalOnLayoutListener(this)
        bitmap = resizeBitmap(bitmap, width, height)
    }

    private fun resizeBitmap(bitmap: Bitmap, reqWidth: Int, reqHeight: Int): Bitmap {
        val matrix = Matrix()
        val src: RectF = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
        val dest: RectF = RectF(0f, 0f, reqWidth.toFloat(), reqHeight.toFloat())
        matrix.setRectToRect(src, dest, Matrix.ScaleToFit.CENTER)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}