package cz.moznabude.floatingcontrol

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import kotlin.math.abs

abstract class MenuView <T: MenuView.Directions>: GridLayout {

    private var isActive = false
    private var isOpened = false
    private val f = Coordinates(0f, 0f)
    private val c = Coordinates(0f, 0f)

    abstract val buttons: Map<T, MenuButton>

    private val handlerOfLongTouch = Handler()
    private val runnable = Runnable {
        isActive = false
        println(c-f)
        println(c-f)
        appear()
        x = c.x - width/2
        y = c.y - height/2
        isOpened = true
    }

    init {
        this.setMenuLayout()
        x = -Float.MAX_VALUE
        y = -Float.MAX_VALUE
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setListener()
    }

    abstract fun setMenuLayout()
    abstract fun getDirection(coordinates: Coordinates): T?

    private fun disappear() { this.visibility = View.GONE }
    private fun appear() { this.visibility = View.VISIBLE }

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
                buttons[getDirection(c-f)]?.function?.invoke()
            }
            true
        }
    }

    data class Coordinates(var x: Float, var y: Float) {
        operator fun minus(other: Coordinates): Coordinates = Coordinates(x-other.x, y-other.y)
        operator fun minusAssign(other: Coordinates) {
            x -= other.x
            y -= other.y
        }
    }
    data class MenuButton(val button: Button, val function: (() -> Unit))

    interface Directions
}