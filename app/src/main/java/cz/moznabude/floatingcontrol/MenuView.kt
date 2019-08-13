package cz.moznabude.floatingcontrol

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import kotlin.math.abs
import kotlin.math.sign

class MenuView: GridLayout {

    private var isActive = false
    private var isOpened = false
    private val f = Coordinates(0f, 0f)
    private val c = Coordinates(0f, 0f)

    private val handlerOfLongTouch = Handler()
    private val runnable = Runnable {
        isActive = false
        println(c.x - f.x)
        println(c.y - f.y)
        appear()
        x = c.x - width/2
        y = c.y - height/2
        isOpened = true
    }

    init {
        View.inflate(context, R.layout.menu, this)
        x = -Float.MAX_VALUE
        y = -Float.MAX_VALUE
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)


    private fun disappear() { this.visibility = View.GONE }
    private fun appear() { this.visibility = View.VISIBLE }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setListener()
    }

    private fun setListener() {
        (parent as ViewGroup).setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                isActive = true
                handlerOfLongTouch.postDelayed(runnable, 1000)
                f.x = event.x
                f.y = event.y
            }

            if (isActive) {
                fun stop() {
                    handlerOfLongTouch.removeCallbacks(runnable)
                    isActive = false
                }
                if (event.actionMasked == MotionEvent.ACTION_MOVE) {
                    c.x = event.x
                    c.y = event.y
                    if (abs(c.x - f.x) > 100 || abs(c.y - f.y) > 100) {
                        stop()
                    }
                }
                if (event.actionMasked == MotionEvent.ACTION_UP || event.actionMasked == MotionEvent.ACTION_POINTER_DOWN) {
                    stop()
                }
            }

            if (event.actionMasked == MotionEvent.ACTION_UP && isOpened) {
                disappear()
                isOpened = false
                c.x = event.x
                c.y = event.y
                val direction = (c.y - f.y) / (c.x - f.x)
                if(sign(c.x-f.x) > 0) {
                    when(direction) {
                        in (-0.5f).rangeTo(0.5f) -> println("next")
                        else -> println("Error")
                    }
                } else {

                }
            }
            true
        }
    }

    private data class Coordinates(var x: Float, var y: Float)
}