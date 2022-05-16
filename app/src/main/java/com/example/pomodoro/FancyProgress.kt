package com.example.pomodoro

import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.CountDownTimer
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.util.concurrent.TimeUnit

class FancyProgress(c: Context, attr: AttributeSet) : View(c, attr) {

    companion object {
        const val MARGIN = 100
        private val FORMAT = "%02d:%02d"
    }

    interface ProgressListener {
        fun progress(value: String)
    }

    var callback: ProgressListener? = null

    private val colors = intArrayOf(
        ContextCompat.getColor(context, R.color.gradiant_start),
        ContextCompat.getColor(context, R.color.gradiant_middle),
        ContextCompat.getColor(context, R.color.gradiant_end)
    )

    private lateinit var baseRecF: RectF
    private val basePaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.white)
        strokeWidth = 60f
        this.style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }

    private val textPaint = TextPaint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
        textSize = 40f
    }

    private lateinit var progressRecF: RectF
    private val progressPaint: Paint

    private lateinit var canvas: Canvas
    private var progress = 360f
    private var time = 0L
    private lateinit var timer: CountDownTimer

    private val r = Rect()
    private val r2 = Rect()
    private val r3 = Rect()

    var text2 = "00:00"

    init {
        val centerOfCanvas = Point(width / 2, height / 2)
        progressPaint = Paint().apply {
            shader = RadialGradient(
                centerOfCanvas.x.toFloat(),
                centerOfCanvas.y.toFloat(),
                800f,
                colors,
                null,
                Shader.TileMode.MIRROR
            )
            strokeWidth = 60f
            this.style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            isAntiAlias = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas
        setup(width, height)

        canvas.drawArc(baseRecF, 360f, 360f, false, basePaint)
        canvas.drawArc(progressRecF, 270f, progress, false, progressPaint)
        drawText(canvas)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun drawText(canvas: Canvas) {
        val text = "Time left"
        val text3 = "P0"

        canvas.getClipBounds(r)
        val cHeight: Int = r.height()
        val cWidth: Int = r.width()
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.textSize = 50f
        textPaint.getTextBounds(text, 0, text.length, r)
        val x: Float = cWidth / 2f - r.width() / 2f - r.left
        val y: Float = cHeight / 2f + (r.height() - (r.height() *2)) / 2f - r.bottom
        canvas.drawText(text, x, y, textPaint)

        canvas.getClipBounds(r2)
        val cHeight2: Int = r2.height()
        val cWidth2: Int = r2.width()
        textPaint.style = Paint.Style.FILL_AND_STROKE
        textPaint.typeface = Typeface.DEFAULT_BOLD
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = 50f
        textPaint.getTextBounds(text, 0, text.length, r2)
        val x2: Float = cWidth2 / 2f - (r2.width() - r.width()) / 2f - r2.left
        val y2: Float = cHeight2 / 2f + (r2.height() + r.height() * 1)  / 2f - r2.bottom
        canvas.drawText(text2, x2, y2, textPaint)

        canvas.getClipBounds(r3)
        val cHeight3: Int = r3.height()
        val cWidth3: Int = r3.width()
        textPaint.textSize = 50f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.typeface = Typeface.DEFAULT
        textPaint.getTextBounds(text, 0, text.length, r3)
        val x3: Float = cWidth3 / 2f - (r3.width() - r2.width()) / 2f - r3.left
        val y3: Float = cHeight3 / 2f + (r3.height() + (r2.height() * 4) )  / 2f - r3.bottom
        canvas.drawText(text3, x3, y3, textPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup(w, h)
    }

    private fun setup(w: Int, h: Int) {
        baseRecF = createRecF(w, h)
        progressRecF = createRecF(w, h)
    }

    private fun createRecF(w: Int, h: Int): RectF {

        val centerOfCanvas = Point(w / 2, h / 2)

        val left: Int = centerOfCanvas.x - (w - MARGIN) / 2
        val top: Int = centerOfCanvas.y - (h - MARGIN) / 2
        val right: Int = centerOfCanvas.x + (w - MARGIN) / 2
        val bottom: Int = centerOfCanvas.y + (h - MARGIN) / 2

        return RectF(
            left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat()
        )

    }

    /*
    *
    * @param progress
    * */
    private fun setProgress(progress: Float) {
        timer = object : CountDownTimer(time, 1) {
            override fun onTick(millisUntilFinished: Long) {
                val remaining = time - millisUntilFinished
                val newProgress = ((remaining * progress) / time)
                callback?.progress(formatTime(millisUntilFinished))
                this@FancyProgress.progress = 360 - newProgress
                this@FancyProgress.text2 = formatTime(millisUntilFinished)
                invalidate()
            }

            override fun onFinish() {
                callback?.progress(formatTime(0))
                this@FancyProgress.progress = 0f
                invalidate()
            }

        }
    }

    fun setTime(time: Long) {
        this.time = time
        callback?.progress(formatTime(time))
        setProgress(360f)
    }

    fun start() {
        timer.start()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        return String.format(
            FORMAT,
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
            ),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
            )
        )
    }

    fun pause() {
        timer.cancel()
    }

}