package cz.moznabude.floatingmenu

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

    var longTouchLength = 1000L
    private var isActive = false
    private var isOpened = false
    private val f = Coordinates(0f, 0f)
    private val c = Coordinates(0f, 0f)

    abstract val buttons: Map<T, MenuButton>

    private val handlerOfLongTouch = Handler()
    private val runnable = Runnable {
        isActive = false
        appear()
        x = c.x - width/2
        y = c.y - height/2
        isOpened = true
    }

    init {
        disappear()
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

    private fun disappear() { this.visibility = View.INVISIBLE }
    private fun appear() { this.visibility = View.VISIBLE }

    private fun setListener() {
        (parent as ViewGroup).setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                isActive = true
                handlerOfLongTouch.postDelayed(runnable, longTouchLength)
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
            isOpened
        }
    }

    private fun getMenuButton(direction: Directions): MenuButton = buttons[direction] ?: throw Exception("No such direction")

    fun setText(direction: Directions, text: String) {
        getMenuButton(direction).button.text = text
    }

    fun setFunction(direction: Directions, function: (() -> Unit)?) {
        getMenuButton(direction).function = function
    }

    fun disableButton(direction: Directions) {
        setFunction(direction, null)
    }

    data class Coordinates(var x: Float, var y: Float) {
        operator fun minus(other: Coordinates): Coordinates =
            Coordinates(x - other.x, y - other.y)
        operator fun minusAssign(other: Coordinates) {
            x -= other.x
            y -= other.y
        }
    }
    class MenuButton(val button: Button) {
        var function: (() -> Unit)? = null
            set(value) {
                if (value == null) {
                    button.visibility = View.INVISIBLE
                } else {
                    button.visibility = View.VISIBLE
                }
                field = value
            }

        init {
            button.visibility = View.INVISIBLE
        }

        constructor(button: Button, function: (() -> Unit)?) : this(button) {
            this.function = function
        }
    }

    interface Directions
}